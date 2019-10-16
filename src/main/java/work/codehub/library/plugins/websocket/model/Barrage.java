package work.codehub.library.plugins.websocket.model;

import lombok.Data;

/**
 * 弹幕消息 .<br>
 *
 * @author andy.sher
 * @date 2019/10/15 14:25
 */
@Data
public class Barrage extends BaseModel {

    /**
     * 文字颜色
     */
    private String color = "white";

    /**
     * 背景颜色
     */
    private String backgroundColor;

    public static Barrage build(String message) {
        Barrage barrage = new Barrage();
        barrage.setType("2");
        barrage.setMessage(message);
        return barrage;
    }

    public static Barrage build(String message, String color, String backgroundColor) {
        Barrage barrage = new Barrage();
        barrage.setType("2");
        barrage.setMessage(message);
        barrage.setColor(color);
        barrage.setBackgroundColor(backgroundColor);
        return barrage;
    }

}
