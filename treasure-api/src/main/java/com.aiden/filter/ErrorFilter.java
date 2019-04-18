package com.aiden.filter;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2019/4/16/016.
 */

/**
 * 过滤器
 */
@Component
@WebFilter(urlPatterns = "/**", filterName = "errorFilter")
public class ErrorFilter implements Filter {

    Logger logger = Logger.getLogger(ErrorFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("controller is error", e);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.append("code:0");
            out.close();
        }
    }

    @Override
    public void destroy() {

    }
}
