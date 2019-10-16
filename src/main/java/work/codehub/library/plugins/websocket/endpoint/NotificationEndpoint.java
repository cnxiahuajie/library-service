package work.codehub.library.plugins.websocket.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import work.codehub.library.plugins.websocket.container.NotificationClientContainer;
import work.codehub.library.plugins.websocket.model.BaseModel;
import work.codehub.library.util.SpringContextUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * 通知类ws终端 .<br>
 *
 * @author andy.sher
 * @date 2019/10/15 13:21
 */
@Slf4j
@Component
@ServerEndpoint("/ws/anon/notification/{cid}")
public class NotificationEndpoint implements IEndpoint {

    private Session session;

    private String cid;

    @OnOpen
    public void onOpen(Session session, @PathParam("access_token") String access_token) {
        this.session = session;
        if ("-".equals(access_token)) {
            this.cid = access_token;
        } else {
            this.cid = session.getId();
        }
        IEndpoint.getContainer(NotificationClientContainer.class).put(cid, session);
        try {
            IEndpoint.sendMessage(session, BaseModel.build("successful"));
        } catch (Exception e) {
            log.error(NotificationClientContainer.class.getName(), e);
        }
    }

    @OnClose
    public void onClose() {
        IEndpoint.getContainer(NotificationClientContainer.class).remove(this.cid);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // 保持心跳
        if (IEndpoint.PING.equals(message)) {
            if (IEndpoint.getContainer(NotificationClientContainer.class).notFound(cid)) {
                IEndpoint.getContainer(NotificationClientContainer.class).put(cid, session);
            }
            try {
                IEndpoint.sendMessage(session, BaseModel.build(IEndpoint.PONG));
            } catch (IOException e) {
                log.error(NotificationClientContainer.class.getName(), e);
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error(NotificationEndpoint.class.getName(), error);
    }

    /**
     * 发送消息 .
     *
     * @param cids  客户端ID
     * @param model 消息模型
     * @return void
     * @author andy.sher
     * @date 2019/10/15 14:18
     */
    public static void sendInfo(String[] cids, BaseModel model) {
        try {
            // 广播
            if (ArrayUtils.isEmpty(cids)) {
                Iterator<Map.Entry<String, Session>> it = IEndpoint.getContainer(NotificationClientContainer.class).entries().iterator();
                while (it.hasNext()) {
                    IEndpoint.sendMessage(it.next().getValue(), model);
                }
            }
            // 指定
            else {
                for (String cid : cids) {
                    IEndpoint.sendMessage(IEndpoint.getContainer(NotificationClientContainer.class).get(cid), model);
                }
            }
        } catch (Exception e) {
            log.error(NotificationEndpoint.class.getName(), e);
        }
    }

    @Scheduled(fixedRate = 30000)
    @Override
    public void ping() {
        NotificationEndpoint.sendInfo(null, SpringContextUtils.getBean(IEndpoint.PING, BaseModel.class));
    }
}
