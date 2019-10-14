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
@TableName("FEEDBACK")
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据主键
     */
    @Id
    @TableId("ID")
    private String id;

    /**
     * 问题反馈内容
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 状态[1=已解决/2=未解决]
     */
    @TableField("STATUS")
    private String status;


}
