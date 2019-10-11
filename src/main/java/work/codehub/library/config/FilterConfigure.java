package work.codehub.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import work.codehub.library.filter.SecurityFilter;

/**
 * 过滤器配置 .<br>
 *
 * @author andy.sher
 * @date 2019/10/11 16:57
 */
@Configuration
public class FilterConfigure {

    @Bean
    public SecurityFilter securityFilter() {
        return new SecurityFilter();
    }

}
