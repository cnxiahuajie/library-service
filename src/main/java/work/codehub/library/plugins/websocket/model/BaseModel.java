package work.codehub.library.plugins.websocket.model;

import lombok.Data;

import java.util.UUID;

/**
 * 基础模型 .<br>
 *
 * @author andy.sher
 * @date 2019/10/15 14:24
 */
@Data
public class BaseModel {

    private String id;

    /**
     * 消息类型[1=普通文字消息/2=弹幕消息]
     */
    private String type;

    private String message;

    public static BaseModel build(String message) {
        BaseModel model = new BaseModel();
        model.setId(UUID.randomUUID().toString());
        model.setType("1");
        model.setMessage(message);
        return model;
    }

}
