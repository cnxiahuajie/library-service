package work.codehub.library.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import work.codehub.library.domain.RArticleCategory;
import work.codehub.library.repository.mapper.RArticleCategoryMapper;
import work.codehub.library.service.IRArticleCategoryService;

/**
 * <p>
 * 文章与文章分类关联表 服务实现类
 * </p>
 *
 * @author codehub
 * @since 2019-09-28
 */
@Service
public class RArticleCategoryServiceImpl extends ServiceImpl<RArticleCategoryMapper, RArticleCategory> implements IRArticleCategoryService {

}
