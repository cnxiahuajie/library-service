package work.codehub.library.plugins.activemq.producer;

import com.alibaba.fastjson.JSONObject;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import work.codehub.library.domain.Barrage;

import javax.annotation.Resource;
import javax.jms.Queue;

/**
 * 弹幕消息生产者 .<br>
 *
 * @author andy.sher
 * @date 2019/10/17 14:56
 */
@Component
public class BarrageMessageProducer {

    @Resource(type = JmsMessagingTemplate.class)
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Resource(name = "barrageMessageQueue")
    private Queue barrageMessageQueue;

    /**
     * 发送弹幕消息 .
     *
     * @param barrage 弹幕消息
     * @return void
     * @author andy.sher
     * @date 2018/9/25 10:59
     */
    public void send(Barrage barrage) {
        String message = JSONObject.toJSONString(barrage);
        jmsMessagingTemplate.convertAndSend(barrageMessageQueue, message);
    }

}
