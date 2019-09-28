package work.codehub.library.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import work.codehub.library.domain.Article;

/**
 * 文章ES仓库 .<br>
 *
 * @author andy.sher
 * @date 2019/9/17 13:10
 */
@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article, String> {

    /**
     * 按文章ID查询文章 .
     *
     * @param id 文章ID
     * @return work.codehub.library.domain.Article 文章信息
     * @author andy.sher
     * @date 2019/9/17 13:11
     */
    Article queryById(String id);

}
