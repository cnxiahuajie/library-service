package work.codehub.library.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 系统配置类 .<br>
 *
 * @author andy.sher
 * @date 2019/7/8 9:57
 */
@Data
@Configuration
public class LibraryProperties {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${work.codehub.library.anon-urls}")
    private String anonUrls;

}
