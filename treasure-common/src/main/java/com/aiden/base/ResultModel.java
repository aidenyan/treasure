package com.aiden.base;


import java.io.Serializable;

/**
 * Created by Administrator on 2019/4/15/015.
 */

public class ResultModel<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private String message;

    private T data;

    private String ticket;

    public ResultModel(ResultCode resultCode) {
        if (resultCode != null) {
            this.code = resultCode.getCode();
            this.message = resultCode.getMessage();
        }
    }
    public ResultModel(String code,String message) {
        this.code = code;
        this.message =message;
    }
    public ResultModel(ResultCode resultCode, T data) {
        if (resultCode != null) {
            this.code = resultCode.getCode();
            this.message = resultCode.getMessage();
        }
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
