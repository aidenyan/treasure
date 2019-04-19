package com.aiden.dto.base;

/**
 * Created by Administrator on 2019/4/15/015.
 */

public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS("200", "成功"),
    /**
     * 失败
     */
    FAIL("-1", "失败"),
    /**
     * 后台数据异常
     */
    ERROR("-2","后台数据异常"),
    /**
     * 登录信息
     */
     LOGIN_FAIL_USER_NOT_EXIST("-20001","用户不存在"),
    LOGIN_FAIL_PASSWORD_ERROR("-20002","密码错误"),
    ;

    private final String code;

    private final String message;

     ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
