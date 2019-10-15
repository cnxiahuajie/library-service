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

    private String message;

    public static BaseModel build(String message) {
        BaseModel model = new BaseModel();
        model.setId(UUID.randomUUID().toString());
        model.setMessage(message);
        return model;
    }

}
