package work.codehub.library.annotation;

import java.lang.annotation.*;

/**
 * 登出 .<br>
 *
 * @author andy.sher
 * @date 2019/10/11 15:25
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Logout {
}
