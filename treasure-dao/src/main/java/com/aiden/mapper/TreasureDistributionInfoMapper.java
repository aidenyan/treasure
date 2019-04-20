package com.aiden.mapper;

import com.aiden.entity.TreasureDistributionInfo;
import org.apache.ibatis.annotations.Param;

public interface TreasureDistributionInfoMapper {


    int insert(TreasureDistributionInfo record);


    TreasureDistributionInfo findById(Long id);

    /**
     * 打开宝藏
     * @param treasureDistributionId
     * @param userId
     * @param isReceive
     */
    int openTreasure(@Param("treasureDistributionId") Long treasureDistributionId,@Param("userId") Long userId,
                     @Param("isReceive") boolean isReceive);

}