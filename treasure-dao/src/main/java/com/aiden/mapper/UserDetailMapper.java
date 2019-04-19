package com.aiden.mapper;

import com.aiden.entity.UserDetail;
import org.apache.ibatis.annotations.Param;

public interface UserDetailMapper {


    UserDetail findByUserId(@Param("userId") Long userId);

    void  update(UserDetail userDetail);

    void insert(UserDetail userDetail);

    int countByCode(@Param("invitationCode") String invitationCode);
}