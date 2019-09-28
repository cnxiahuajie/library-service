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
 * 文章类别
 * </p>
 *
 * @author sme_cloud
 * @since 2019-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ARTICLE_CATEGORY")
public class ArticleCategory implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId("ID")
    private String id;

    /**
     * 类别名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 状态
     */
    @TableField("STATUS")
    private String status;


}
