package com.aiden.filter;

import com.aiden.base.ResultCode;
import com.aiden.base.ResultModel;
import com.aiden.exception.ParamException;
import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Administrator on 2019/4/16/016.
 */
@Component
@WebFilter(urlPatterns = "/**", filterName = "errorFilter")
public class ErrorFilter implements Filter {

    Logger logger = Logger.getLogger(ErrorFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Long start = System.currentTimeMillis();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String url = request.getRequestURI();
        try {
            filterChain.doFilter(servletRequest, response);
        }catch (Exception e) {
            logger.error("controller is error", e);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            OutputStream out = response.getOutputStream();
            ResultModel<Void> resultModel = new ResultModel<>(ResultCode.EXCEPTION);
            out.write(JSON.toJSONString(resultModel).getBytes());
            out.flush();
            out.close();
        }
        logger.debug("url[" + url + "](" + (System.currentTimeMillis() - start)+"ms)");
    }

    @Override
    public void destroy() {

    }
}
