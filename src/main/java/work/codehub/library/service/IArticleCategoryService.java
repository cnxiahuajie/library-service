package work.codehub.library.service;

import com.baomidou.mybatisplus.extension.service.IService;
import work.codehub.library.domain.ArticleCategory;
import work.codehub.library.pojo.ArticleCategoryVO;

import java.util.List;

/**
 * <p>
 * 文章类别 服务类
 * </p>
 *
 * @author sme_cloud
 * @since 2019-09-28
 */
public interface IArticleCategoryService extends IService<ArticleCategory> {

    /**
     * 按文章主键获取文章分类列表 .
     *
     * @param articleId 文章主键
     * @return java.util.List<work.codehub.library.pojo.ArticleCategoryVO> 文章分类列表
     * @author andy.sher
     * @date 2019/9/28 14:36
     */
    List<ArticleCategoryVO> listByArticleId(String articleId);
}
