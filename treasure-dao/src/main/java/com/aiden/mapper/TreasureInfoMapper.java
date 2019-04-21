package com.aiden.mapper;

import com.aiden.entity.TreasureInfo;
import com.aiden.entity.UnreceiveTreasure;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TreasureInfoMapper {
    List<TreasureInfo> findAll();


    int insert(TreasureInfo record);

    TreasureInfo findById(Long id);

    int update(TreasureInfo record);

    int updateTreasureAmount(@Param("id") Long id, @Param("alreadyAmount") BigDecimal alreadyAmount, @Param("newAlreadyAmount") BigDecimal newAlreadyAmount);

     List<UnreceiveTreasure> findUnReceiveTreasure(@Param("lat")BigDecimal lat,@Param("lng")BigDecimal lng,@Param("distance")BigDecimal distance);
}