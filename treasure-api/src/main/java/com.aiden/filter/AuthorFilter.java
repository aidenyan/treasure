package com.aiden.filter;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by Administrator on 2019/4/16/016.
 */

/**
 * 过滤器
 */
@Component
@WebFilter(urlPatterns = "/**", filterName = "errorFilter")
public class AuthorFilter implements Filter {

    Logger logger = Logger.getLogger(AuthorFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
