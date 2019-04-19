package com.aiden.exception;

/**
 * Created by Administrator on 2019/4/19/019.
 */
public class FormatException extends RuntimeException {
    public FormatException(){

    }
    public FormatException(String message){
        super(message);
    }
}
