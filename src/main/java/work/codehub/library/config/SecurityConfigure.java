package work.codehub.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import work.codehub.library.aspect.SecurityAspect;

/**
 * 安全配置 .<br>
 *
 * @author andy.sher
 * @date 2019/10/11 15:01
 */
@Configuration
public class SecurityConfigure {

    /**
     * 初始化安全切面 .
     *
     * @param
     * @return work.codehub.library.aspect.SecurityAspect
     * @author andy.sher
     * @date 2019/10/11 15:13
     */
    @Bean
    public SecurityAspect securityAspect() {
        return new SecurityAspect();
    }

}
