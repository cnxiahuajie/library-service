package work.codehub.library.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 文章与文章分类关联表
 * </p>
 *
 * @author sme_cloud
 * @since 2019-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("R_ARTICLE_CATEGORY")
public class RArticleCategory implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId("ID")
    private String id;

    /**
     * 文章主键
     */
    @TableField("ARTICLE_ID")
    private String articleId;

    /**
     * 文章分类主键
     */
    @TableField("ARTICLE_CATEGORY_ID")
    private String articleCategoryId;


}
