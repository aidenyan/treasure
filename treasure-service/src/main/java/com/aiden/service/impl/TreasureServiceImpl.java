package com.aiden.service.impl;

import com.aiden.common.enums.TreasureLevelEnum;
import com.aiden.common.enums.TreasureTypeEnum;
import com.aiden.entity.TreasureDistributionInfo;
import com.aiden.entity.TreasureInfo;
import com.aiden.entity.UserDetail;
import com.aiden.exception.ServiceException;
import com.aiden.exception.UpdateException;
import com.aiden.mapper.TreasureDistributionInfoMapper;
import com.aiden.mapper.TreasureInfoMapper;
import com.aiden.service.SysConfigService;
import com.aiden.service.TreasureService;
import com.aiden.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2019/4/19/019.
 */
@Service
@Transactional(readOnly = true)
public class TreasureServiceImpl implements TreasureService {

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private TreasureInfoMapper treasureInfoMapper;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private TreasureDistributionInfoMapper treasureDistributionInfoMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void saveTreasureInfo(TreasureInfo info) {
        if (info.getId() != null) {
            treasureInfoMapper.update(info);
        } else {
            treasureInfoMapper.insert(info);
        }
    }

    @Override
    public TreasureDistributionInfo findTreasureDistributionInfo(Long treasureDistributionId) {
        Assert.notNull(treasureDistributionId, "treasureDistributionId is null");
        return treasureDistributionInfoMapper.findById(treasureDistributionId);
    }

    @Override
    public TreasureInfo findTreasureInfo(Long treasureId) {
        Assert.notNull(treasureId, "treasureId is null");
        return treasureInfoMapper.findById(treasureId);
    }
    @Transactional( rollbackFor = {Exception.class})
    public void openFailTreasure(TreasureLevelEnum treasureLevelEnum, Long treasureDistributionId, Long userId){
        openTreasure(treasureLevelEnum,treasureDistributionId,userId,false,null,null,null,null,null);
    }

    @Transactional( rollbackFor = {Exception.class})
    public void openNotAmountSuccessTreasure(TreasureLevelEnum treasureLevelEnum, Long treasureDistributionId, Long userId){
        openTreasure(treasureLevelEnum,treasureDistributionId,userId,true,null,null,null,null,null);
    }

    @Override
    @Transactional( rollbackFor = {Exception.class})
    public void openTreasure(TreasureLevelEnum treasureLevelEnum, Long treasureDistributionId, Long userId, boolean isReceive,
                             Long sysConfigId, BigDecimal sysAlreadyAmount,
                             BigDecimal treasureAlreadyAmount, BigDecimal amount, Integer alreadyNum) {
        Assert.notNull(treasureDistributionId);
        Assert.notNull(userId);
        Assert.notNull(isReceive);
        int resultInt = treasureDistributionInfoMapper.openTreasure(treasureDistributionId, userId, isReceive);
        if (resultInt != 1) {
            throw new UpdateException("更新失败处理");
        }
        if (!isReceive) {
            return;
        }
        Assert.notNull(treasureLevelEnum);
        if (amount == null||BigDecimal.ZERO.compareTo(amount)==0) {
            return;
        }

        if (TreasureLevelEnum.LOW_LEVEL == treasureLevelEnum) {
            sysConfigService.updateTreasureLow(sysConfigId, alreadyNum);
        } else if (TreasureLevelEnum.HIGHT_LEVEL == treasureLevelEnum) {
            sysConfigService.updateTreasureHeight(sysConfigId, alreadyNum);
        }
        sysConfigService.updateTreasureAmount(sysConfigId, sysAlreadyAmount, amount);
        TreasureDistributionInfo treasureDistributionInfo=treasureDistributionInfoMapper.findById(treasureDistributionId);
        if(treasureDistributionInfo==null){
            throw new ServiceException("系统数据有更新异常");
        }
        updateTreasureAmount(treasureDistributionInfo.getTreasureId(),treasureAlreadyAmount,amount);
        userDetailService.updateBalanceAmount(userId,amount);
    }


    private void updateTreasureAmount(Long id, BigDecimal alreadyAmount, BigDecimal amount) {
        Assert.notNull(amount);
        Assert.notNull(id);
        if(alreadyAmount!=null){
            amount=alreadyAmount.add(amount);
        }
        int resultInt = treasureInfoMapper.updateTreasureAmount(id, alreadyAmount,amount);
        if (resultInt != 1) {
            throw new UpdateException("更新失败处理");
        }
    }
}
