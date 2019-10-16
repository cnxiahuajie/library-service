package work.codehub.library.plugins.websocket.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import work.codehub.library.plugins.websocket.container.CommandClientContainer;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * 命令行类ws终端 .<br>
 *
 * @author andy.sher
 * @date 2019/10/15 13:20
 */
@Slf4j
@Component
@ServerEndpoint("/ws/command/{cid}")
public class CommandEndpoint implements IEndpoint {

    private Session session;

    private String cid;

    @OnOpen
    public void onOpen(Session session, @PathParam("cid") String cid) {
        this.session = session;
        this.cid = cid;
        IEndpoint.getContainer(CommandClientContainer.class).put(cid, session);
    }

    @OnClose
    public void onClose() {
        IEndpoint.getContainer(CommandClientContainer.class).remove(this.cid);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // 接收命令

        // 解析命令

        // 执行命令

        // 响应结果
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error(CommandEndpoint.class.getName(), error);
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    @Override
    public void ping() {

    }
}
