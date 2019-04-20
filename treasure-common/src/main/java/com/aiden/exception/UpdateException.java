package com.aiden.exception;

/**
 * Created by Administrator on 2019/4/19/019.
 */
public class UpdateException extends RuntimeException {
    public UpdateException(){

    }
    public UpdateException(String message){
        super(message);
    }
}
