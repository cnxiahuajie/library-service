package work.codehub.library.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 安全过滤器 .<br>
 *
 * @author andy.sher
 * @date 2019/10/11 16:48
 */
@Slf4j
public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = servletRequest.getParameter("access_token");
        log.info(token);
    }

    @Override
    public void destroy() {

    }

}
