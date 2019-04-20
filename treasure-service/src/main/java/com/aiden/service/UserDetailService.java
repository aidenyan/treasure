package com.aiden.service;

import com.aiden.entity.UserDetail;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2019/4/19/019.
 */
public interface UserDetailService {

    UserDetail findByUserId(Long userId);

    void save(UserDetail userDetail);

    /**
     * 获取唯一的邀请码
     * @return
     */
    String findInvitationCode();

    void updateBalanceAmount(Long userId, BigDecimal amount);

}
