package work.codehub.library.plugins.websocket.endpoint;

import com.alibaba.fastjson.JSONObject;
import work.codehub.library.plugins.websocket.container.BaseContainer;
import work.codehub.library.plugins.websocket.model.BaseModel;
import work.codehub.library.util.SpringContextUtils;

import javax.websocket.Session;
import java.io.IOException;

/**
 * WS终端 .<br>
 *
 * @author andy.sher
 * @date 2019/10/15 14:03
 */
public interface IEndpoint {

    static String PING = "ping";
    static String PONG = "pong";

    /**
     * 获取客户端容器 .
     *
     * @param clazz 容器类型
     * @return work.codehub.library.plugins.websocket.container.BaseContainer 客户端容器
     * @author andy.sher
     * @date 2019/10/15 14:05
     */
    static BaseContainer getContainer(Class<? extends BaseContainer> clazz) {
        return SpringContextUtils.getBean(clazz);
    }

    /**
     * 发送消息 .
     *
     * @param session 会话
     * @param model   消息模型
     * @return void
     * @author andy.sher
     * @date 2019/10/15 14:47
     */
    static void sendMessage(Session session, BaseModel model) throws IOException {
        if (null != session && session.isOpen()) {
            session.getBasicRemote().sendText(JSONObject.toJSONString(model));
        }
    }

}
