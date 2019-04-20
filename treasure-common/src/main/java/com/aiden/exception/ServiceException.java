package com.aiden.exception;

/**
 * Created by Administrator on 2019/4/19/019.
 */
public class ServiceException extends RuntimeException {
    public ServiceException(){

    }
    public ServiceException(String message){
        super(message);
    }
}
