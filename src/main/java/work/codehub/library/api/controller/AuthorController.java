package work.codehub.library.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.codehub.library.api.model.RequestEntity;
import work.codehub.library.api.model.ResponseEntity;
import work.codehub.library.domain.Author;
import work.codehub.library.domain.RArticleCategory;
import work.codehub.library.pojo.AuthorVO;
import work.codehub.library.service.IAuthorService;
import work.codehub.library.service.IRArticleCategoryService;
import work.codehub.library.util.BeanUtils;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1")
public class AuthorController {

    @Resource
    private IAuthorService authorService;

    @Resource
    private IRArticleCategoryService irArticleCategoryService;

    @PostMapping("/author/synchronization")
    public ResponseEntity synchronization(@RequestBody RequestEntity requestEntity) {
        AuthorVO authorVO = JSONObject.parseObject(requestEntity.getData(), AuthorVO.class);

        ResponseEntity responseEntity;

        LambdaQueryWrapper<Author> authorLambdaQueryWrapper = new QueryWrapper<Author>().lambda();
        authorLambdaQueryWrapper.eq(Author::getEmail, authorVO.getEmail());
        Author author = authorService.getOne(authorLambdaQueryWrapper);

        // 保存作者信息
        if (null == author) {
            authorService.save(authorVO);
            responseEntity = ResponseEntity.build(HttpStatus.CREATED, authorVO);
        } else {
            String id = author.getId();
            authorVO.setId(id);
            BeanUtils.copySourceToTarget(authorVO, author);
            author.setId(id);
            authorService.updateById(author);
            responseEntity = ResponseEntity.build(HttpStatus.OK, author);
        }

        // 保存作者和文章类型关联信息
        // 删除旧类别列表
        LambdaUpdateWrapper<RArticleCategory> rArticleCategoryLambdaUpdateWrapper = new UpdateWrapper<RArticleCategory>().lambda();
        rArticleCategoryLambdaUpdateWrapper.eq(RArticleCategory::getTargetId, authorVO.getId());
        rArticleCategoryLambdaUpdateWrapper.eq(RArticleCategory::getTargetType, "2");
        irArticleCategoryService.remove(rArticleCategoryLambdaUpdateWrapper);
        // 添加新类别
        if (CollectionUtils.isNotEmpty(authorVO.getInterests())) {
            authorVO.getInterests().forEach(interest -> {
                RArticleCategory rArticleCategory = new RArticleCategory();
                rArticleCategory.setTargetId(authorVO.getId());
                rArticleCategory.setArticleCategoryId(interest.getId());
                rArticleCategory.setTargetType("2");
                irArticleCategoryService.save(rArticleCategory);
            });
        }

        return responseEntity;
    }

}
