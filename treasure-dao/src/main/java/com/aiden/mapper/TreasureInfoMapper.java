package com.aiden.mapper;

import com.aiden.entity.TreasureInfo;

public interface TreasureInfoMapper {
    int deleteByPrimaryKey(Long id);


    int insert(TreasureInfo record);

    TreasureInfo selectByPrimaryKey(Long id);

    int update(TreasureInfo record);

    int updateByPrimaryKey(TreasureInfo record);
}