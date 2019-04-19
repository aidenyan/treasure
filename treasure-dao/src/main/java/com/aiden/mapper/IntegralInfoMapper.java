package com.aiden.mapper;

import com.aiden.entity.IntegralInfo;

public interface IntegralInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IntegralInfo record);

    int insertSelective(IntegralInfo record);

    IntegralInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IntegralInfo record);

    int updateByPrimaryKey(IntegralInfo record);
}