package work.codehub.library.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import work.codehub.library.domain.ArticleCategory;
import work.codehub.library.pojo.ArticleCategoryVO;
import work.codehub.library.repository.mapper.ArticleCategoryMapper;
import work.codehub.library.service.IArticleCategoryService;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 文章类别 服务实现类
 * </p>
 *
 * @author sme_cloud
 * @since 2019-09-28
 */
@Service
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper, ArticleCategory> implements IArticleCategoryService {

    @Resource
    private ArticleCategoryMapper articleCategoryMapper;

    @Override
    public List<ArticleCategoryVO> listByArticleId(String articleId) {
        return articleCategoryMapper.listByArticleId(articleId);
    }
}
