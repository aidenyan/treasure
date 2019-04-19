package com.aiden.mapper;

import com.aiden.entity.TreasureFindInfo;

public interface TreasureFindInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TreasureFindInfo record);

    int insertSelective(TreasureFindInfo record);

    TreasureFindInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TreasureFindInfo record);

    int updateByPrimaryKey(TreasureFindInfo record);
}