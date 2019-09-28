package work.codehub.library.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author codehub
 * @since 2019-08-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("AUTHOR")
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据主键
     */
    @Id
    @TableId("ID")
    private String id;

    /**
     * 昵称
     */
    @TableField("NAME")
    private String name;

    /**
     * 邮箱
     */
    @TableField("EMAIL")
    private String email;


}
