package com.aiden.dto.base;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/4/15/015.
 */
@Data
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

    public ResultModel(ResultCode resultCode, T data) {
        if (resultCode != null) {
            this.code = resultCode.getCode();
            this.message = resultCode.getMessage();
        }
        this.data = data;
    }
}
