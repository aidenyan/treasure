package com.aiden.service;

import com.aiden.entity.SysConfig;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2019/4/19/019.
 */
public interface SysConfigService {
    SysConfig findOne();


    /**
     * 处理更新领取红包
     */
    void updateTreasureLow(Long id, Integer lowNum);

    /**
     * 处理更新领取红包
     */
    void updateTreasureHeight(Long id, Integer heightNum);
    void updateTreasureAmount(Long id,  BigDecimal alreadyAmount,BigDecimal amount);
}
