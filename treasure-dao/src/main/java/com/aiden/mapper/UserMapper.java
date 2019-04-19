package com.aiden.mapper;

import com.aiden.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    /**
     * 保存
     * @param record
     * @return
     */
    int insert(User record);

    /**
     * 根据手机号码查询
     * @param mobile
     * @return
     */
    User findByMobile(@Param("mobile") String mobile);


    /**
     * 根据token查询
     * @param token
     * @return
     */
    User findToken(@Param("token") String token);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    User find(@Param("id") Long id);

    /**
     * 更新
     * @param record
     */
    int update(User record);




}