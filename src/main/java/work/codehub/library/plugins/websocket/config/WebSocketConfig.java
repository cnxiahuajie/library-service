package work.codehub.library.plugins.websocket.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import work.codehub.library.plugins.websocket.constants.WSConstant;
import work.codehub.library.plugins.websocket.endpoint.IEndpoint;
import work.codehub.library.plugins.websocket.model.WSMessage;
import work.codehub.library.pojo.BarrageVO;
import work.codehub.library.util.StringUtils;

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

    @Bean(name = IEndpoint.PING)
    public WSMessage ping() {
        BarrageVO barrageVO = new BarrageVO();
        barrageVO.setContent(IEndpoint.PING);
        return WSMessage.builder().id(StringUtils.getUUID()).type(WSConstant.TYPE_COMMAND).message(JSONObject.toJSONString(barrageVO)).build();
    }

    @Bean(name = IEndpoint.PONG)
    public WSMessage pong() {
        BarrageVO barrageVO = new BarrageVO();
        barrageVO.setContent(IEndpoint.PONG);
        return WSMessage.builder().id(StringUtils.getUUID()).message(JSONObject.toJSONString(barrageVO)).type(WSConstant.TYPE_COMMAND).build();
    }

    @Bean(name = IEndpoint.LOGOUT)
    public WSMessage logout() {
        BarrageVO barrageVO = new BarrageVO();
        barrageVO.setContent(IEndpoint.LOGOUT);
        return WSMessage.builder().id(StringUtils.getUUID()).message(JSONObject.toJSONString(barrageVO)).type(WSConstant.TYPE_COMMAND).build();
    }

}
