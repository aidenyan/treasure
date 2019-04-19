package com.aiden.service.impl;

import com.aiden.common.utils.StringUtils;
import com.aiden.entity.UserDetail;
import com.aiden.mapper.UserDetailMapper;
import com.aiden.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = false, rollbackFor = {Exception.class})
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
}
