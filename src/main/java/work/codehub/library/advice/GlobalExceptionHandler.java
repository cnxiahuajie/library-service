package work.codehub.library.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import work.codehub.library.api.model.ResponseEntity;
import work.codehub.library.util.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 统一异常处理器 .<br>
 *
 * @author andy.sher
 * @date 2018/7/10 17:18
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
        log.info("成功初始化统一异常处理器");
    }

    /**
     * 获取栈异常信息 .<br>
     *
     * @param throwable 异常对象
     * @return java.lang.String
     * @author andy.sher
     * @date 2018/8/1 10:38
     */
    private String getStackTrace(Throwable throwable) {
        String errorInfo = StringUtils.EMPTY;
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw);) {
            // 将出错的栈信息输出到printWriter中
            throwable.printStackTrace(pw);
            pw.flush();
            sw.flush();
            errorInfo = sw.toString();
        } catch (IOException e) {
            log.error(GlobalExceptionHandler.class.getName(), e);
        }
        return errorInfo;
    }

    /**
     * 默认异常处理器 .<br>
     *
     * @param e 异常对象
     * @return com.vteam.sme.api.entity.RespEntity 响应对象
     * @author andy.sher
     * @date 2018/7/11 14:38
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity defaultErrorHandler(Exception e) {
        log.error(getStackTrace(e));
        return ResponseEntity.error();
    }

    /**
     * 非法参数处理器 .<br>
     *
     * @param e 异常对象
     * @return ResponseEntity 响应对象
     * @author andy.sher
     * @date 2018/7/11 14:38
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity illegalArgumentException(Exception e) {
        log.error(getStackTrace(e));
        return ResponseEntity.error(e.getMessage());
    }

}
