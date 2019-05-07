package com.aiden.service.impl;

import com.aiden.common.enums.TypeEnum;
import com.aiden.common.utils.DateUtils;
import com.aiden.entity.*;
import com.aiden.exception.ServiceException;
import com.aiden.exception.UpdateException;
import com.aiden.mapper.IntegralInfoMapper;
import com.aiden.mapper.TreasureFindInfoMapper;
import com.aiden.mapper.TreasurePointInfoMapper;
import com.aiden.mapper.UserMapper;
import com.aiden.service.UserDetailService;
import com.aiden.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by Administrator on 2019/4/19/019.
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IntegralInfoMapper integralInfoMapper;
    @Autowired
    private TreasurePointInfoMapper treasurePointInfoMapper;
    @Autowired
    private TreasureFindInfoMapper treasureFindInfoMapper;
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
    @Transactional(rollbackFor = {Exception.class})
    public void regUser(User record, User invitedUser) {
        update(record);
        TreasureFindInfo treasureFindInfo=new TreasureFindInfo();
        treasureFindInfo.setPoint(5);
        treasureFindInfo.setType(TypeEnum.REG.getType());
        treasureFindInfo.setUserId(record.getId());
        treasureFindInfo.setCreatedDate(DateUtils.now());
        treasureFindInfoMapper.insert(treasureFindInfo);
        TreasurePointInfo treasurePointInfo=new TreasurePointInfo();
        treasurePointInfo.setPoint(5);
        treasurePointInfo.setType(TypeEnum.REG.getType());
        treasurePointInfo.setUserId(record.getId());
        treasurePointInfo.setCreatedDate(DateUtils.now());
        treasurePointInfoMapper.insert(treasurePointInfo);
        if(invitedUser!=null){
            update(invitedUser);
            IntegralInfo integralInfo=new IntegralInfo();
            integralInfo.setPoint(1);
            integralInfo.setType(TypeEnum.INVITED.getType());
            integralInfo.setUserId(invitedUser.getId());
            integralInfo.setCreatedDate(DateUtils.now());
            integralInfoMapper.insert(integralInfo);
             treasureFindInfo=new TreasureFindInfo();
            treasureFindInfo.setPoint(1);
            treasureFindInfo.setType(TypeEnum.INVITED.getType());
            treasureFindInfo.setUserId(invitedUser.getId());
            treasureFindInfo.setCreatedDate(DateUtils.now());
            treasureFindInfoMapper.insert(treasureFindInfo);
             treasurePointInfo=new TreasurePointInfo();
            treasurePointInfo.setPoint(1);
            treasurePointInfo.setType(TypeEnum.INVITED.getType());
            treasurePointInfo.setUserId(invitedUser.getId());
            treasurePointInfo.setCreatedDate(DateUtils.now());
            treasurePointInfoMapper.insert(treasurePointInfo);
        }
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public void update(User user, UserDetail userDetail) {
        if (user != null) {
            if (user.getId() != null) {
                userMapper.update(user);
            } else {
                userMapper.insert(user);
            }
        }
        if (userDetail != null) {
            if(user!=null&&user.getId()!=null){
                userDetail.setUserId(user.getId());
            }

            userDetailService.save(userDetail);
        }
    }

    @Override
    public void update(UserDetail userDetail) {
        userDetailService.save(userDetail);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public void updateTreasurePoint(Long userId, Integer updatePoint) {
        Assert.notNull(userId);
        Assert.notNull(updatePoint);
        User user = userMapper.find(userId);
        if(user==null){
            throw new ServiceException("系统数据错误");
        }

        if(user.getTreasurePoint()!=null){
            updatePoint=user.getTreasurePoint()+updatePoint;
        }
        if(updatePoint<0){
            throw new UpdateException("更新数据错误");
        }
       Integer resultInt= userMapper.updateTreasurePoint(userId,user.getTreasurePoint(),updatePoint);
        if (resultInt != 1) {
            throw new UpdateException("更新失败处理");
        }






    }
}
