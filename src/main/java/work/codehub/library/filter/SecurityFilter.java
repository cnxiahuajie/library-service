package work.codehub.library.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;
import work.codehub.library.config.LibraryProperties;
import work.codehub.library.repository.redis.TokenRedisTemplate;
import work.codehub.library.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 安全过滤器 .<br>
 *
 * @author andy.sher
 * @date 2019/10/11 16:48
 */
@Slf4j
@Order(2)
public class SecurityFilter implements Filter {

    private String TOKEN = "access_token";

    private String SESSION_ID = "_session_id";

    @Resource
    private TokenRedisTemplate tokenRedisTemplate;

    @Resource
    private LibraryProperties libraryProperties;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        String contextPath = servletRequest.getRequestURI();

        boolean isAnon = isAnonResource(contextPath);
        boolean isValid = (authenticated(request.getParameter(TOKEN)) && checkAuthorization(contextPath));
        // 匿名访问资源或（已登录并且有权）的情况下直接放行
        if (isAnon || isValid) {
            filterChain.doFilter(request, response);
        } else {
            servletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "没有权限。");
            return;
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isAnonResource(String path) {
        String[] anonResources = libraryProperties.getAnonUrls().split(",");
        return PatternMatchUtils.simpleMatch(anonResources, path);
    }

    /**
     * 校验token .
     *
     * @param token token
     * @return boolean 是否校验通过
     * @author andy.sher
     * @date 2019/10/14 17:25
     */
    private boolean authenticated(String token) {
        return StringUtils.isNotBlank(token) && null != tokenRedisTemplate.get(token);
    }

    /**
     * 校验权限 .
     *
     * @param path 资源路径
     * @return boolean 是否校验通过
     * @author andy.sher
     * @date 2019/10/14 17:25
     */
    private boolean checkAuthorization(String path) {
        // TODO 暂不实现，待后期有需求再实现
        return true;
    }


}
