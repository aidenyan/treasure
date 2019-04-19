package com.aiden.mapper;

import com.aiden.entity.TreasurePointInfo;

public interface TreasurePointInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TreasurePointInfo record);

    int insertSelective(TreasurePointInfo record);

    TreasurePointInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TreasurePointInfo record);

    int updateByPrimaryKey(TreasurePointInfo record);
}