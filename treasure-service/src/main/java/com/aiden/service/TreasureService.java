package com.aiden.service;

import com.aiden.base.Page;
import com.aiden.common.enums.TreasureLevelEnum;
import com.aiden.entity.TreasureDistributionInfo;
import com.aiden.entity.TreasureInfo;
import com.aiden.entity.UnreceiveTreasure;
import com.aiden.entity.UserTreasure;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2019/4/19/019.
 */
public interface TreasureService {
    void saveTreasureInfo(TreasureInfo info);

    TreasureDistributionInfo findTreasureDistributionInfo(Long treasureDistributionId);
    void save(TreasureDistributionInfo treasureDistributionInfo);

    TreasureInfo findTreasureInfo(Long treasureId);

    List<TreasureInfo> findAll();;

    /**
     * 打开宝藏
     */
    void openTreasure(TreasureLevelEnum treasureLevelEnum, Long treasureDistributionId, Long userId, boolean isReceive,
                      Long sysConfigId, BigDecimal sysAlreadyAmount,
                      BigDecimal treasureAlreadyAmount, BigDecimal amount,Integer alreadyNum) ;
    void openFailTreasure(TreasureLevelEnum treasureLevelEnum, Long treasureDistributionId, Long userId);
    void openNotAmountSuccessTreasure(TreasureLevelEnum treasureLevelEnum, Long treasureDistributionId, Long userId);


    List<UnreceiveTreasure> findUnReceiveTreasure(BigDecimal lat, BigDecimal lng,BigDecimal distance);


    Page<UserTreasure,Void> pageUserTreasure( Long userId,  Boolean isReceive, Integer currentPage,Integer pageSize);

}
