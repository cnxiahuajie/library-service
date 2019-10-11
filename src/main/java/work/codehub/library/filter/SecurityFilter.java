package work.codehub.library.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
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

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = request.getParameter("access_token");
        log.info(token);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
