package work.codehub.library.plugins.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import work.codehub.library.plugins.websocket.model.Barrage;
import work.codehub.library.plugins.websocket.model.BaseModel;

/**
 * websocket配置 .<br>
 *
 * @author andy.sher
 * @date 2019/10/15 13:17
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public BaseModel ping() {
        return Barrage.build("富强、民主、文明、和谐、自由、平等、公正、法治、爱国、敬业、诚信、友善", "yellow", "red");
    }

}
