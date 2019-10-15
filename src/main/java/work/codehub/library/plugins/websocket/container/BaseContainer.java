package work.codehub.library.plugins.websocket.container;

import javax.websocket.Session;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话容器 .<br>
 *
 * @author andy.sher
 * @date 2019/10/15 13:54
 */
public class BaseContainer {

    private Map<String, Session> container = new ConcurrentHashMap<>();

    public void put(String cid, Session session) {
        this.container.put(cid, session);
    }

    public void remove(String cid) {
        this.container.remove(cid);
    }

    public Session get(String cid) {
        return this.container.get(cid);
    }

    public Set<Map.Entry<String, Session>> entries() {
        return this.container.entrySet();
    }

}
