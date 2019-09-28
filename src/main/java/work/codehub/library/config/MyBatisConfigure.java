package work.codehub.library.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis配置 .<br>
 *
 * @author andy.sher
 * @date 2019/8/1 16:18
 */
@Configuration
@MapperScan(basePackages = {"work.codehub.library.repository.mapper"})
public class MyBatisConfigure {
}
