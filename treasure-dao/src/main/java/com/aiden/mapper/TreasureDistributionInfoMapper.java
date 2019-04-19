package com.aiden.mapper;

import com.aiden.entity.TreasureDistributionInfo;

public interface TreasureDistributionInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TreasureDistributionInfo record);

    int insertSelective(TreasureDistributionInfo record);

    TreasureDistributionInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TreasureDistributionInfo record);

    int updateByPrimaryKey(TreasureDistributionInfo record);
}