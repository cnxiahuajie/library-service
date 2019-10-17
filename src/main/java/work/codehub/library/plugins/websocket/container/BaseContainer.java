package work.codehub.library.plugins.websocket.container;

import work.codehub.library.util.StringUtils;

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
        if (StringUtils.isNotBlank(cid) && null != session) {
            this.container.put(cid, session);
        }
    }

    public void remove(String cid) {
        if (StringUtils.isNotBlank(cid)) {
            this.container.remove(cid);
        }
    }

    public Session get(String cid) {
        if (StringUtils.isNotBlank(cid)) {
            return this.container.get(cid);
        } else {
            return null;
        }
    }

    public Set<Map.Entry<String, Session>> entries() {
        return this.container.entrySet();
    }

    public String[] keys() {
        String[] keyArray = new String[this.container.keySet().size()];
        return this.container.keySet().toArray(keyArray);
    }

    public boolean notFound(String cid) {
        if (StringUtils.isNotBlank(cid)) {
            return !this.container.containsKey(cid) || null == this.container.get(cid) || !this.container.get(cid).isOpen();
        } else {
            return true;
        }
    }

}
