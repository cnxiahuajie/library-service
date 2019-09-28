package work.codehub.library.pojo;

import lombok.Data;
import work.codehub.library.domain.Article;

import java.util.List;

/**
 * 文章视图扩展类 .
 *
 * @author andy.sher
 * @date 2019/8/31 11:03
 */
@Data
public class ArticleVO extends Article {

    private static final long serialVersionUID = 1966076015135655347L;

    /**
     * 文章类别列表
     */
    private List<ArticleCategoryVO> articleCategories;

    /**
     * 邮箱
     */
    private String email;

}
