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
     * 后台数据异常
     */
    EXCEPTION("-5","处理异常"),
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
    LOGIN_FAIL_SMS_ERROR("-20006","请距离上次发送时间距离60s后在发"),
    LOGIN_FAIL_ALREADY_REG("-20007","该用户已经注册了"),
    LOGIN_FAIL_SEND_CODE("-20008","请先发送验证码"),
    LOGIN_FAIL_VERIFY_CODE_ERROR("-20009","验证码错误"),
    LOGIN_FAIL_VERIFY_DATE_ERROR("-20009","验证时间已经超过30分钟了"),
    LOGIN_FAIL_ALREADY_INVITED_ERROR("-20010","已经填写过邀请码了"),
    LOGIN_FAIL_SEND_NUM("-20011","发送次数已经超过5次"),
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

    /**
     * 用户相关信息
     */
    USER_INFO_PARAM_BLANK("-50001","上传的参数必须有一个"),
    /**
     * 现金
     */
    CASH_FAIL_NOT_ENOUGH("-60001","余额不足"),
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
