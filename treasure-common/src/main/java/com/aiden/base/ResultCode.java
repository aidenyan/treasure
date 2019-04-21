package com.aiden.base;

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
    ERROR("-2","参数数据错误"),
    /**
     * 无权限
     */
    AUTHOR("-3","无权限"),
    /**
     * 登录信息
     */
     LOGIN_FAIL_USER_NOT_EXIST("-20001","用户不存在"),
    LOGIN_FAIL_PASSWORD_ERROR("-20002","密码错误"),
    LOGIN_FAIL_INVITED_ERROR("-20003","请填写正确的邀请码"),
    LOGIN_FAIL_INVITED_SELF_ERROR("-20004","邀请码不能填写自己的"),
    LOGIN_FAIL_INVITED_USER_ERROR("-20005","该用户非新注册用户"),
    /**
     *
     */
    USER_FAIL_PASSWORD_ERROR("-30001","原密码错误"),

    /**
     *挖宝信息
     */
    TREASURE_FAIL_ALREADY_RECIEVE("-40001","已经被人挖取"),
    /**
     * 活动结束
     */
    TREASURE_FAIL_ALREADY_END("-40002","活动已经结束"),
    /**
     * 活动没有启动
     */
    TREASURE_FAIL_NOT_ENBALED("-40003","活动没有启动"),
    /**
     * 活动红包已经领取
     */
    TREASURE_FAIL_COMPLETED("-40004","活动没有启动"),
    TREASURE_FAIL_NOT_EXIST("-40005","宝藏类型不存在"),
    TREASURE_FAIL_NOT_POINT("-40006","用户已经没有寻宝点了"),
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
