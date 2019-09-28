package work.codehub.library.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import work.codehub.library.domain.Article;
import work.codehub.library.repository.mapper.ArticleMapper;
import work.codehub.library.service.IArticleService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sme_cloud
 * @since 2019-08-31
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
