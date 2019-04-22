package com.aiden.mapper;

import com.aiden.entity.TreasureDistributionInfo;
import com.aiden.entity.UserTreasure;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TreasureDistributionInfoMapper {


    int insert(TreasureDistributionInfo record);


    TreasureDistributionInfo findById(Long id);

    /**
     * 打开宝藏
     *
     * @param treasureDistributionId
     * @param userId
     * @param isReceive
     */
    int openTreasure(@Param("treasureDistributionId") Long treasureDistributionId, @Param("userId") Long userId,
                     @Param("isReceive") boolean isReceive);

    int countUserTreasure(@Param("userId") Long userId, @Param("isReceive") Boolean isReceive);

    List<UserTreasure> listUserTreasure(@Param("userId") Long userId, @Param("isReceive") Boolean isReceive,@Param("start")Integer start,@Param("pageSize")Integer pageSize);
}