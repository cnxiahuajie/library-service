package work.codehub.library.plugins.activemq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import work.codehub.library.plugins.websocket.constants.WSConstant;
import work.codehub.library.plugins.websocket.endpoint.BarrageEndpoint;
import work.codehub.library.plugins.websocket.model.WSMessage;

import java.util.UUID;

/**
 * 弹幕消息消费者 .<br>
 *
 * @author andy.sher
 * @date 2019/10/17 15:01
 */
@Component
public class BarrageMessageConsumer {

    @JmsListener(destination = "${spring.activemq.queue.barrage}")
    public void receive(String message) {
        BarrageEndpoint.sendInfo(null, WSMessage.builder().id(UUID.randomUUID().toString()).type(WSConstant.TYPE_BARRAGE).message(message).build());
    }

}
