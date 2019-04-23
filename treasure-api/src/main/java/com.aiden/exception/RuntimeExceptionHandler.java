package com.aiden.exception;

import com.aliyuncs.utils.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/23/023.
 */
@ControllerAdvice
public class RuntimeExceptionHandler {
    @ResponseBody
    @ExceptionHandler(UnloginException.class)
    public Map<String,Object> unloginHandler(UnloginException e){
        Map<String,Object> result = new HashMap<>();
        result.put("code", 0);
        if(StringUtils.isEmpty(e.getMessage())){
            result.put("message", "用户已经在其他地方登录，请重现登录");
        }else{
            result.put("message",e.getMessage());
        }

        return result;
    }
    @ResponseBody
    @ExceptionHandler(FileException.class)
    public Map<String,Object> exceptionHandler(FileException e ){
        Map<String,Object> result = new HashMap<>();
        result.put("code", 0);
        if(StringUtils.isEmpty(e.getMessage())){
            result.put("message", "文件处理异常");
        }else{
            result.put("message",e.getMessage());
        }
        return result;
    }
}
