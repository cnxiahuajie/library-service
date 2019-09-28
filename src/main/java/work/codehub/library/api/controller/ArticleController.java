package work.codehub.library.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import work.codehub.library.api.model.RequestEntity;
import work.codehub.library.api.model.ResponseEntity;
import work.codehub.library.constants.CommonConstants;
import work.codehub.library.domain.Article;
import work.codehub.library.domain.RArticleCategory;
import work.codehub.library.pojo.ArticleCategoryVO;
import work.codehub.library.pojo.ArticleVO;
import work.codehub.library.repository.elasticsearch.ArticleRepository;
import work.codehub.library.service.IArticleCategoryService;
import work.codehub.library.service.IArticleService;
import work.codehub.library.service.IRArticleCategoryService;
import work.codehub.library.util.BeanUtils;
import work.codehub.library.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * 文章服务接口控制器 .<br>
 *
 * @author andy.sher
 * @date 2019/8/31 11:04
 */
@Slf4j
@RestController
@RequestMapping("/v1")
public class ArticleController {

    @Resource
    private IArticleService articleService;

    @Resource
    private ArticleRepository articleRepository;

    @Resource
    private IArticleCategoryService articleCategoryService;

    @Resource
    private IRArticleCategoryService irArticleCategoryService;

    @PostMapping(value = "/article")
    public ResponseEntity add(@RequestBody RequestEntity requestEntity) {
        ArticleVO articleVO = JSONObject.parseObject(requestEntity.getData(), ArticleVO.class);
        // 保存文章信息
        articleService.save(articleVO);
        // 保存文章索引
        articleRepository.save(articleVO);
        return ResponseEntity.build(HttpStatus.CREATED, articleVO);
    }

    @DeleteMapping(value = "/article/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        Assert.notNull(id, "请选择文章。");
        Article article = articleService.getById(id);
        // 删除索引
        articleRepository.delete(article);
        // 删除文章
        articleService.removeById(id);
        return ResponseEntity.build(HttpStatus.OK);
    }

    @PatchMapping(value = "/article")
    public ResponseEntity update(@RequestBody RequestEntity requestEntity) {
        ArticleVO articleVO = JSONObject.parseObject(requestEntity.getData(), ArticleVO.class);
        Assert.notNull(articleVO.getId(), "数据主键不存在。");
        // 更新文章信息
        articleService.updateById(articleVO);

        // 更新文章类别
        // 删除旧类别列表
        LambdaUpdateWrapper<RArticleCategory> wr = new UpdateWrapper<RArticleCategory>().lambda();
        wr.eq(RArticleCategory::getTargetId, articleVO.getId());
        wr.eq(RArticleCategory::getTargetType, "1");
        irArticleCategoryService.remove(wr);
        // 添加新类别
        if (CollectionUtils.isNotEmpty(articleVO.getArticleCategories())) {
            articleVO.getArticleCategories().forEach(category -> {
                RArticleCategory rArticleCategory = new RArticleCategory();
                rArticleCategory.setTargetId(articleVO.getId());
                rArticleCategory.setArticleCategoryId(category.getId());
                rArticleCategory.setTargetType("1");
                irArticleCategoryService.save(rArticleCategory);
            });
        }

        // 更新索引
        articleRepository.delete(articleVO);
        articleVO.setContent(StringUtils.stripHtml(articleVO.getContent()));
        articleRepository.save(articleVO);
        return ResponseEntity.build(HttpStatus.OK, articleVO);
    }

    @GetMapping(value = "/article/{id}")
    public ResponseEntity get(@PathVariable("id") String id) {
        Assert.notNull(id, "数据主键不存在。");
        Article article = articleService.getById(id);
        ArticleVO articleVO = BeanUtils.copy(article, ArticleVO.class);
        // 组装文章类别
        List<ArticleCategoryVO> articleCategories = articleCategoryService.listByArticleId(articleVO.getId());
        if (CollectionUtils.isNotEmpty(articleCategories)) {
            articleVO.setArticleCategories(articleCategories);
        }

        return ResponseEntity.build(HttpStatus.OK, articleVO);
    }

    @PostMapping("/article/upload")
    public ResponseEntity upload(HttpServletRequest request) {
        MultiValueMap<String, MultipartFile> multipartFileMultiValueMap = ((MultipartHttpServletRequest) request)
                .getMultiFileMap();
        MultipartFile multipartFile = multipartFileMultiValueMap.getFirst("file");
        String title = multipartFile.getOriginalFilename();
        String tmpFilePath = StringUtils.getTmpPath() + UUID.randomUUID().toString();
        File file = new File(tmpFilePath);
        List<String> lines = null;
        try (InputStream is = multipartFile.getInputStream(); OutputStream os = new FileOutputStream(file)) {
            IOUtils.copy(is, os);
            lines = FileUtils.readLines(file, CommonConstants.Character.UTF_8);
        } catch (IOException e) {
            log.error(ArticleController.class.getName(), e);
        }

        StringBuffer stringBuffer = new StringBuffer();
        if (CollectionUtils.isNotEmpty(lines)) {
            lines.forEach(line -> stringBuffer.append(line));
        } else {
            new IllegalArgumentException("文章内容不能为空。");
        }

        ArticleVO articleVO = new ArticleVO();
        articleVO.setTitle(title.substring(0, title.lastIndexOf(CommonConstants.Symbol.DOT)));
        articleVO.setContent(StringUtils.stripXSS(stringBuffer.toString()));
        articleService.save(articleVO);
        // 保存索引
        articleVO.setContent(StringUtils.stripHtml(articleVO.getContent()));
        articleRepository.save(articleVO);

        return ResponseEntity.build(HttpStatus.CREATED);
    }

    @GetMapping("/article/search/{q}/{p}")
    public ResponseEntity search(@PathVariable("q") String query, @PathVariable("p") Integer page) {
        if (CommonConstants.Symbol.MINUS.equals(query.trim())) {
            query = StringUtils.EMPTY;
        }
        HighlightBuilder.Field field = new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>");
        SearchQuery searchQuery = new NativeSearchQueryBuilder().
                withQuery(new MatchQueryBuilder("content", query))
                .withHighlightFields(field)
                .withPageable(PageRequest.of(page, 10)).build();
        Page<Article> articlePage = articleRepository.search(searchQuery);
        return ResponseEntity.build(HttpStatus.OK, articlePage);
    }

}
