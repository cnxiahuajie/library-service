package work.codehub.library.aspect;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import work.codehub.library.api.model.ResponseEntity;
import work.codehub.library.repository.redis.TokenRedisTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 安全相关的切面 .<br>
 *
 * @author andy.sher
 * @date 2019/10/11 15:12
 */
@Aspect
public class SecurityAspect {

    @Resource
    private TokenRedisTemplate tokenRedisTemplate;

    @Around("@annotation(work.codehub.library.annotation.Login)")
    public Object login(ProceedingJoinPoint joinPoint) throws Throwable {

        Object responseData = joinPoint.proceed();
        if (responseData instanceof ResponseEntity) {
            ResponseEntity responseEntity = (ResponseEntity) responseData;
            if (HttpStatus.OK.value() == responseEntity.getCode()) {
                Object data = responseEntity.getData();
                JSONObject jsonData;
                if (null == data) {
                    jsonData = new JSONObject();
                } else {
                    jsonData = JSONObject.parseObject(JSONObject.toJSONString(responseEntity.getData()));
                }
                jsonData.put("token", UUID.randomUUID().toString());
                tokenRedisTemplate.add(jsonData.getString("token"));
                responseEntity.setData(jsonData);
            }
        }

        return responseData;
    }

    @Around("@annotation(work.codehub.library.annotation.Logout)")
    public Object logout(ProceedingJoinPoint joinPoint) throws Throwable {

        Object responseData = joinPoint.proceed();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        tokenRedisTemplate.delete(request.getParameter("access_token"));

        return responseData;
    }

}
