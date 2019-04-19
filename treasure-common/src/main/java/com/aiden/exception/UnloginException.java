package com.aiden.exception;

/**
 * Created by Administrator on 2019/4/19/019.
 */
public class UnloginException extends RuntimeException {
    public UnloginException(){

    }
    public UnloginException(String message){
          super(message);
    }
}
