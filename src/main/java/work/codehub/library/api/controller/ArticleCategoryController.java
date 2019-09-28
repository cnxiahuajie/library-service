package work.codehub.library.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.codehub.library.api.model.ResponseEntity;
import work.codehub.library.domain.ArticleCategory;
import work.codehub.library.service.IArticleCategoryService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文章分类接口控制器 .<br>
 *
 * @author andy.sher
 * @date 2019/9/28 14:14
 */
@Slf4j
@RestController
@RequestMapping("/v1")
public class ArticleCategoryController {

    @Resource
    private IArticleCategoryService articleCategoryService;

    @GetMapping("/categories")
    public ResponseEntity list() {
        List<ArticleCategory> articleCategories = articleCategoryService.list();
        return ResponseEntity.build(HttpStatus.OK, articleCategories);
    }

}
