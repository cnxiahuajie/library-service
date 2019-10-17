package work.codehub.library.plugins.websocket.model;

import lombok.Builder;
import lombok.Data;

/**
 * 基础模型 .<br>
 *
 * @author andy.sher
 * @date 2019/10/15 14:24
 */
@Data
@Builder
public class WSMessage {

    private String id;

    /**
     * 消息类型[1=弹幕消息/2=命令]
     */
    private String type;

    private String message;

}
