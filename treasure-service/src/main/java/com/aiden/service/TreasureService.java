package com.aiden.service;

import com.aiden.common.enums.TreasureLevelEnum;
import com.aiden.entity.TreasureDistributionInfo;
import com.aiden.entity.TreasureInfo;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2019/4/19/019.
 */
public interface TreasureService {
    void saveTreasureInfo(TreasureInfo info);

    TreasureDistributionInfo findTreasureDistributionInfo(Long treasureDistributionId);

    TreasureInfo findTreasureInfo(Long treasureId);

    /**
     * 打开宝藏
     */
    void openTreasure(TreasureLevelEnum treasureLevelEnum, Long treasureDistributionId, Long userId, boolean isReceive,
                      Long sysConfigId, BigDecimal sysAlreadyAmount,
                      BigDecimal treasureAlreadyAmount, BigDecimal amount,Integer alreadyNum) ;
    void openFailTreasure(TreasureLevelEnum treasureLevelEnum, Long treasureDistributionId, Long userId);
    void openNotAmountSuccessTreasure(TreasureLevelEnum treasureLevelEnum, Long treasureDistributionId, Long userId);

}
