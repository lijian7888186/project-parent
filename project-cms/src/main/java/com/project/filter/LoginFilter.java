package com.project.filter;

import com.project.util.redis.RedisService;
import com.project.utils.BeansManager;
import com.project.utils.StaticFields;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * describe:
 *
 * @author
 */
public class LoginFilter implements Filter{
    private RedisService redisService;
    /**
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        redisService = BeansManager.getBean(RedisService.class);
    }

    /**
     * @param request
     * @param response
     * @param chain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String servletPath = httpServletRequest.getServletPath();
        if (servletPath.contains(".")) {
            String[] split = servletPath.split(".");
            if (split[1].equals("js") || split[1].equals("css") || split[1].equals("png") || split[1].equals("jpg")
                    || split[1].equals("html")) {
                chain.doFilter(request, response);
            }
        } else {
            if (servletPath.equals("/login/toLogin")) {
                chain.doFilter(request, response);
            } else {
                Cookie[] cookies = httpServletRequest.getCookies();
                String jsessionId = null;
                for (Cookie cookie : cookies) {
                    if (StaticFields.EMPPREFIX.equals(cookie)) {
                        jsessionId = cookie.getValue();
                        break;
                    }
                }
                if (StringUtils.isNotBlank(jsessionId)) {
                    String userId = redisService.get(jsessionId);
                    if (StringUtils.isNotBlank(userId)) {
                        String value = redisService.get(userId);
                        if (!jsessionId.equals(value)) {
                            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                            httpServletResponse.sendRedirect("/loing.html");
                            return;
                        }
                        redisService.expire(jsessionId, 1800);
                        redisService.expire(userId, 1800);
                    }
                }
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.sendRedirect("/loing.html");
                return;
            }
        }
    }

    @Override
    public void destroy() {

    }
}
