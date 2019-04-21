package com.aiden.mapper;

import com.aiden.entity.CashInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CashInfoMapper {
    int count(@Param("userId") Long userId);

    List<CashInfo> findList(@Param("userId") Long userId, @Param("start") Integer start, @Param("pageSize") Integer pageSize);
   int insert(CashInfo cashInfo);
}