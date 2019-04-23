package com.aiden.mapper;

import com.aiden.entity.CashInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CashInfoMapper {
    int count(@Param("userId") Long userId,@Param("type")Integer type);

    List<CashInfo> findList(@Param("userId") Long userId,@Param("type")Integer type, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    int insert(CashInfo cashInfo);

    int updateStatus(@Param("status") Integer status, @Param("id") Long id);
}