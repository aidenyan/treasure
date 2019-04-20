package com.aiden.mapper;

import com.aiden.entity.TreasureInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface TreasureInfoMapper {
    int deleteByPrimaryKey(Long id);


    int insert(TreasureInfo record);

    TreasureInfo findById(Long id);

    int update(TreasureInfo record);

    int updateTreasureAmount(@Param("id") Long id, @Param("alreadyAmount") BigDecimal alreadyAmount, @Param("newAlreadyAmount") BigDecimal newAlreadyAmount);

}