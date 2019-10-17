package work.codehub.library.plugins.activemq.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

/**
 * Activemq配置信息
 *
 * @author zack.yin
 * @date 2018/7/13 9:35
 */
@Slf4j
@Configuration
public class ActivemqConfig {

    @Value("${spring.activemq.queue.barrage}")
    private String barrageMessageQueue;

    /**
     * websocket 弹幕消息发送队列 .
     *
     * @param
     * @return javax.jms.Topic 消息发送队列
     * @author andy.sher
     * @date 2019/1/9 15:02
     */
    @Bean("barrageMessageQueue")
    public Queue barrageMessageQueue() {
        return new ActiveMQQueue(barrageMessageQueue);
    }

}
