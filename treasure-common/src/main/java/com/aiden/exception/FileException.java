package com.aiden.exception;

/**
 * Created by Administrator on 2019/4/19/019.
 */
public class FileException extends RuntimeException {
    public FileException(){

    }
    public FileException(String message){
        super(message);
    }
}
