package com.aiden.mapper;

import com.aiden.entity.TreasureInfo;

public interface TreasureInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TreasureInfo record);

    int insertSelective(TreasureInfo record);

    TreasureInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TreasureInfo record);

    int updateByPrimaryKey(TreasureInfo record);
}