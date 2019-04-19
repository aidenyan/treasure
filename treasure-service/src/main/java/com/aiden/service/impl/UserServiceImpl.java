package com.aiden.service.impl;

import com.aiden.entity.User;
import com.aiden.entity.UserDetail;
import com.aiden.mapper.UserMapper;
import com.aiden.service.UserDetailService;
import com.aiden.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2019/4/19/019.
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDetailService userDetailService;

    @Override
    public boolean isExist(String mobile) {
        User user = userMapper.findByMobile(mobile);
        return user != null;
    }

    @Override
    public User findByMobile(String mobile) {
        return userMapper.findByMobile(mobile);
    }


    @Override
    public User findToken(String token) {
        return userMapper.findToken(token);
    }

    @Override
    public User find(Long id) {
        return userMapper.find(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public void update(User record) {
        userMapper.update(record);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public void update(User user, UserDetail userDetail) {
        if (user != null) {
            if(user.getId()!=null){
                userMapper.update(user);
            }else{
                userMapper.insert(user);
            }
        }
        if(userDetail!=null){
            userDetail.setUserId(user.getId());
        }
        userDetailService.save(userDetail);
    }
}
