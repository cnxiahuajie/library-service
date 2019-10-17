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
@TableName("BARRAGE")
public class Barrage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据主键
     */
    @Id
    @TableId("ID")
    private String id;

    /**
     * 弹幕类型[1=广播/2=定向/3=文章]
     */
    @TableField("TYPE")
    private String type;

    /**
     * 弹幕文字颜色
     */
    @TableField("COLOR")
    private String color;

    /**
     * 弹幕文字
     */
    @TableField("CONTENT")
    private String content;

    /**
     * 接收人
     */
    @TableField("TARGET")
    private String target;


}
