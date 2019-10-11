package work.codehub.library.annotation;

import java.lang.annotation.*;

/**
 * 登录 .<br>
 *
 * @author andy.sher
 * @date 2019/10/11 15:16
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
}
