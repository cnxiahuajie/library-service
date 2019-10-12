package work.codehub.library.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import work.codehub.library.helper.LocalStore;
import work.codehub.library.model.TokenInfo;
import work.codehub.library.repository.redis.TokenRedisTemplate;
import work.codehub.library.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LocalStoreAspect {

    @Resource
    private TokenRedisTemplate tokenRedisTemplate;

    @Around("execution(* work.codehub.library.api.controller..*.*(..))")
    public Object login(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getParameter("access_token");
        if (StringUtils.isNotBlank(token)) {
            TokenInfo tokenInfo = tokenRedisTemplate.get(token);
            if (null != tokenInfo) {
                LocalStore.setToken(tokenInfo.getToken());
                LocalStore.setAuthor(tokenInfo.getAuthorVO());
            }
        }
        Object responseData = joinPoint.proceed();

        LocalStore.destory();

        return responseData;
    }

}
