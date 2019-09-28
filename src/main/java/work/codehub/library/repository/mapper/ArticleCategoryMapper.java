package work.codehub.library.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import work.codehub.library.domain.ArticleCategory;
import work.codehub.library.pojo.ArticleCategoryVO;

import java.util.List;

/**
 * <p>
 * 文章类别 Mapper 接口
 * </p>
 *
 * @author codehub
 * @since 2019-09-28
 */
public interface ArticleCategoryMapper extends BaseMapper<ArticleCategory> {

    /**
     * 按文章主键获取文章分类列表 .
     *
     * @param articleId 文章主键
     * @return java.util.List<work.codehub.library.pojo.ArticleCategoryVO> 文章分类列表
     * @author andy.sher
     * @date 2019/9/28 14:37
     */
    List<ArticleCategoryVO> listByArticleId(@Param("articleId") String articleId);
}
