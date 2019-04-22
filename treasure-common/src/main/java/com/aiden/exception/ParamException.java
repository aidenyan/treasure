package com.aiden.exception;

/**
 * Created by Administrator on 2019/4/19/019.
 */
public class ParamException extends RuntimeException {
    public ParamException(){

    }
    public ParamException(String message){
        super(message);
    }
}
