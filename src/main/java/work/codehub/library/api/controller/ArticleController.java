package work.codehub.library.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import work.codehub.library.api.model.RequestEntity;
import work.codehub.library.api.model.ResponseEntity;
import work.codehub.library.constants.CommonConstants;
import work.codehub.library.constants.DomainConstants;
import work.codehub.library.domain.Article;
import work.codehub.library.domain.ArticleCategory;
import work.codehub.library.domain.Author;
import work.codehub.library.domain.RArticleCategory;
import work.codehub.library.helper.LocalStore;
import work.codehub.library.plugins.activemq.producer.BarrageMessageProducer;
import work.codehub.library.plugins.websocket.config.BarrageColor;
import work.codehub.library.pojo.ArticleCategoryVO;
import work.codehub.library.pojo.ArticleVO;
import work.codehub.library.pojo.AuthorVO;
import work.codehub.library.pojo.BarrageVO;
import work.codehub.library.repository.elasticsearch.ArticleRepository;
import work.codehub.library.service.*;
import work.codehub.library.util.BeanUtils;
import work.codehub.library.util.StringUtils;

import javax.annotation.Resource;
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

    @Resource
    private IAuthorService authorService;

    @Resource
    private IBarrageService barrageService;

    @Resource
    private BarrageMessageProducer barrageMessageProducer;

    @PostMapping(value = "/article")
    public ResponseEntity add(@RequestBody RequestEntity requestEntity) {
        ArticleVO articleVO = JSONObject.parseObject(requestEntity.getData(), ArticleVO.class);
        articleVO.setAuthorId(LocalStore.getAuthor().getId());
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

        String cuurentAuthorId = LocalStore.getAuthor().getId();
        String articleAuthorId = article.getAuthorId();
        // 如果当前认证用户和文章作者相同，则允许操作，否则401
        if (cuurentAuthorId.equals(articleAuthorId)) {
            // 删除索引
            articleRepository.delete(BeanUtils.copy(article, ArticleVO.class));
            // 删除文章
            articleService.removeById(id);
            return ResponseEntity.build(HttpStatus.OK);
        } else {
            return ResponseEntity.unauthorized("没有权限。");
        }
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

    @GetMapping(value = "/anon/article/{id}")
    public ResponseEntity get(@PathVariable("id") String id) {
        Assert.notNull(id, "数据主键不存在。");
        Article article = articleService.getById(id);
        ArticleVO articleVO = BeanUtils.copy(article, ArticleVO.class);
        article.setViewCount(article.getViewCount() + 1);
        articleService.updateById(article);
        // 组装文章类别
        List<ArticleCategoryVO> articleCategories = articleCategoryService.listByArticleId(articleVO.getId());
        if (CollectionUtils.isNotEmpty(articleCategories)) {
            articleVO.setArticleCategories(articleCategories);
        }

        return ResponseEntity.build(HttpStatus.OK, articleVO);
    }

    @PostMapping("/article/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile multipartFile, @RequestParam("categories") String[] categories) {
        // 获取文件名
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
        articleVO.setAuthorId(LocalStore.getAuthor().getId());
        articleService.save(articleVO);
        // 保存索引
        articleVO.setContent(StringUtils.stripHtml(articleVO.getContent()));
        articleRepository.save(articleVO);

        // 获取文章分类
        if (ArrayUtils.isNotEmpty(categories)) {
            ArticleCategory articleCategory;
            RArticleCategory rArticleCategory = null;
            for (String category : categories) {
                articleCategory = articleCategoryService.getById(category);
                if (null != articleCategory) {
                    rArticleCategory = new RArticleCategory();
                    rArticleCategory.setTargetId(articleVO.getId());
                    rArticleCategory.setTargetType("1");
                    rArticleCategory.setArticleCategoryId(category);
                    irArticleCategoryService.save(rArticleCategory);
                }
            }
        }

        // 保存弹幕
        BarrageVO barrageVO = new BarrageVO();
        barrageVO.setType(DomainConstants.Barrage.TYPE_1);
        barrageVO.setContent(String.format("%s刚刚发布了【%s】", LocalStore.getAuthor().getName(), articleVO.getTitle()));
        barrageVO.setColor(BarrageColor.Color.BLACK);
        barrageService.save(barrageVO);

        // 推送弹幕
        barrageMessageProducer.send(barrageVO);

        return ResponseEntity.build(HttpStatus.CREATED);
    }

    @GetMapping("/anon/article/search/{q}/{p}")
    public ResponseEntity search(@PathVariable("q") String query, @PathVariable("p") Integer page) {
        if (CommonConstants.Symbol.MINUS.equals(query.trim())) {
            query = StringUtils.EMPTY;
        }
        MatchQueryBuilder titleQuery = QueryBuilders.matchQuery("title", query);
        MatchQueryBuilder contentQuery = QueryBuilders.matchQuery("content", query);
        QueryBuilder totalFilter = QueryBuilders.boolQuery().filter(titleQuery).should(contentQuery);

        SearchQuery searchQuery = new NativeSearchQueryBuilder().
                withQuery(totalFilter)
                .withPageable(PageRequest.of(page, 10)).build();
        Page<ArticleVO> articlePage = articleRepository.search(searchQuery);

        List<ArticleVO> articles = articlePage.getContent();
        if (CollectionUtils.isNotEmpty(articles)) {
            articles.forEach(article -> {
                // 组装文章分类
                List<ArticleCategoryVO> categories = articleCategoryService.listByArticleId(article.getId());
                article.setArticleCategories(categories);
                // 组装作者信息
                Author author = authorService.getById(article.getAuthorId());
                article.setAuthorVO(BeanUtils.copy(author, AuthorVO.class));
            });
        }

        return ResponseEntity.build(HttpStatus.OK, articlePage);
    }

}
