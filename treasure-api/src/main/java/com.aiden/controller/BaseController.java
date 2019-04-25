package com.aiden.controller;

import com.aiden.exception.ParamException;

/**
 * Created by Administrator on 2019/4/19/019.
 */
public class BaseController {
    public final static String key = "login.aiden.yanyifei.com.md5";
    public static final String AUTHOR_KEY = "5npkwbx2luq0c8gchdc9tasaf9v9wj6f";
    public static final Integer TOATL_SEND_NUM=5;
    public void veriftyTrue(boolean result, String message) {
           if(!result){
               throw new ParamException(message);
           }
    }
}
