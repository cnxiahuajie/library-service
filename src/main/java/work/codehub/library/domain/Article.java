package work.codehub.library.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author codehub
 * @since 2019-08-31
 */
@Document(indexName = "library", type = "article")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ARTICLE")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据主键
     */
    @Id
    @TableId("ID")
    private String id;

    /**
     * 标题
     */
    @TableField("TITLE")
    private String title;

    /**
     * 文章内容
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    @TableField("CONTENT")
    private String content;

    /**
     * 状态[1=草稿/2=待发布/3=已发布]
     */
    @TableField("STATUS")
    private String status;


}
