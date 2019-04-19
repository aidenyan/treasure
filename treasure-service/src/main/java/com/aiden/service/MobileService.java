package com.aiden.service;

/**
 * Created by Administrator on 2019/4/19/019.
 */
public interface MobileService {
    /**
     * 发送短信信息
     *
     * @param mobile  手机号码
     * @param content 短信内容
     * @return 发送的结果
     */
    boolean sendMobile(String mobile, String content);
}
