package com.aiden.service.impl;

import com.aiden.common.utils.StringUtils;
import com.aiden.entity.UserDetail;
import com.aiden.exception.UpdateException;
import com.aiden.mapper.UserDetailMapper;
import com.aiden.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2019/4/19/019.
 */
@Service
@Transactional(readOnly = true)
public class UserDetailServiceImpl implements UserDetailService {
    @Autowired
    private UserDetailMapper userDetailMapper;
    @Override
    public UserDetail findByUserId(Long userId) {
        return userDetailMapper.findByUserId(userId);
    }

    @Override
    public UserDetail findByInvitedCode(String invitedCode) {
        Assert.isTrue(!org.springframework.util.StringUtils.isEmpty(invitedCode));
        return userDetailMapper.findByInvitedCode(invitedCode);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void save(UserDetail userDetail) {
        if(userDetail==null){
            return;
        }
        if(userDetail.getId()!=null){
            userDetailMapper.update(userDetail);
        }else{
            userDetailMapper.insert(userDetail);
        }
    }

    @Override
    public String findInvitationCode() {
        String code=StringUtils.random(8);
        while (userDetailMapper.countByCode(code)>0){
            code=StringUtils.random(8);
        }
        return code;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updateBalanceAmount(Long userId, BigDecimal amount) {
        if(amount==null){
            return;
        }
         UserDetail userDetail=userDetailMapper.findByUserId(userId);
         if(userDetail==null){
             throw new UpdateException("用户信息不存在");
         }
         BigDecimal updateAmount=amount;
         if(userDetail.getBalanceAmount()!=null){
             updateAmount=updateAmount.add(userDetail.getBalanceAmount());
         }


       int resultInt=  userDetailMapper.updateBalanceAmount(userDetail.getId(),userDetail.getBalanceAmount(),updateAmount);
        if (resultInt != 1) {
            throw new UpdateException("更新失败处理");
        }
    }
}
