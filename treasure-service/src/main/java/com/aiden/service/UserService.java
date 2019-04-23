package com.aiden.service;

import com.aiden.entity.User;
import com.aiden.entity.UserDetail;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2019/4/19/019.
 */
public interface UserService {
    /**
     * 检查手机号是否存在
     *
     * @param mobile 手机号码
     * @return 存在
     */
    boolean isExist(String mobile);

    /**
     * 根据手机号码查询
     *
     * @param mobile
     * @return
     */
    User findByMobile(@Param("mobile") String mobile);


    /**
     * 根据token查询
     *
     * @param token
     * @return
     */
    User findToken(@Param("token") String token);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    User find(@Param("id") Long id);

    /**
     * 更新
     *
     * @param record
     */
    void update(User record);

    /**
     * 更新
     *
     * @param record
     */
    void updateInvition(User record,User invitedUser);

    /**
     * 保存信息
     *
     * @param user
     * @param userDetail
     */
    void update(User user, UserDetail userDetail);
    /**
     * 保存信息
     *
     * @param userDetail
     */
    void update(UserDetail userDetail);

    void updateTreasurePoint(Long userId, Integer updatePoint);
}
