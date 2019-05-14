package com.aiden.service.impl;

import com.aiden.base.Page;
import com.aiden.common.enums.BalanceTypeEnum;
import com.aiden.common.enums.StatusEnum;
import com.aiden.common.enums.TreasureLevelEnum;
import com.aiden.common.utils.DateUtils;
import com.aiden.entity.*;
import com.aiden.exception.ServiceException;
import com.aiden.exception.UpdateException;
import com.aiden.mapper.TreasureDistributionInfoMapper;
import com.aiden.mapper.TreasureInfoMapper;
import com.aiden.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
    private UserService userService;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private CashInfoService cashInfoService;

    @Autowired
    private TreasureDistributionInfoMapper treasureDistributionInfoMapper;


    @Transactional(rollbackFor = {Exception.class})
    public void saveTreasureInfo(TreasureInfo info) {
        if (info.getId() != null) {
            treasureInfoMapper.update(info);
        } else {
            treasureInfoMapper.insert(info);
        }
    }


    public TreasureDistributionInfo findTreasureDistributionInfo(Long treasureDistributionId) {
        Assert.notNull(treasureDistributionId, "treasureDistributionId is null");
        return treasureDistributionInfoMapper.findById(treasureDistributionId);
    }


    @Transactional(rollbackFor = {Exception.class})
    public void save(TreasureDistributionInfo treasureDistributionInfo) {
        Assert.notNull(treasureDistributionInfo);
        treasureDistributionInfoMapper.insert(treasureDistributionInfo);
    }


    public TreasureInfo findTreasureInfo(Long treasureId) {
        Assert.notNull(treasureId, "treasureId is null");
        return treasureInfoMapper.findById(treasureId);
    }

    @Override

    @Transactional(rollbackFor = {Exception.class})
    public void updateEndTime(Date endTime) {
        treasureInfoMapper.updateEndTime(endTime);
    }


    public List<TreasureInfo> findAll() {
        return treasureInfoMapper.findAll();
    }

    @Transactional(rollbackFor = {Exception.class})
    public void openFailTreasure(TreasureLevelEnum treasureLevelEnum, Long treasureDistributionId, Long userId) {
        openTreasure(treasureLevelEnum, treasureDistributionId, userId, false, null, null, null, null, null);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void openNotAmountSuccessTreasure(TreasureLevelEnum treasureLevelEnum, Long treasureDistributionId, Long userId) {
        openTreasure(treasureLevelEnum, treasureDistributionId, userId, true, null, null, null, null, null);
    }

    public List<UnreceiveTreasure> findUnReceiveTreasure(BigDecimal lat, BigDecimal lng, BigDecimal distance) {
        Assert.notNull(lat);
        Assert.notNull(lng);
        return treasureInfoMapper.findUnReceiveTreasure(lat, lng, distance);
    }

    @Override
    public Page<UserTreasure, Void> pageUserTreasure(Long userId, Boolean isReceive, Integer currentPage, Integer pageSize) {
        Assert.notNull(pageSize);
        Assert.notNull(currentPage);
        Assert.notNull(userId);
      Integer total=  treasureDistributionInfoMapper.countUserTreasure(userId,isReceive);
        Page<UserTreasure,Void> page=new Page<>();
        page.setTotal(total);
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        if(total==0){
            return page;
        }
        page.setResult(treasureDistributionInfoMapper.listUserTreasure(userId,isReceive,(page.getCurrentPage()-1)*page.getPageSize(),page.getPageSize()));
        return page;
    }

    @Transactional(rollbackFor = {Exception.class})
    public void openTreasure(TreasureLevelEnum treasureLevelEnum, Long treasureDistributionId, Long userId, boolean isReceive,
                             Long sysConfigId, BigDecimal sysAlreadyAmount,
                             BigDecimal treasureAlreadyAmount, BigDecimal amount, Integer alreadyNum) {
        Assert.notNull(treasureDistributionId);
        Assert.notNull(userId);
        Assert.notNull(isReceive);
        int resultInt = treasureDistributionInfoMapper.openTreasure(treasureDistributionId, userId, isReceive);
        userService.updateTreasurePoint(userId,-1);
        if (resultInt != 1) {
            throw new UpdateException("更新失败处理");
        }
        if (!isReceive) {
            return;
        }
        Assert.notNull(treasureLevelEnum);
        if (amount == null || BigDecimal.ZERO.compareTo(amount) == 0) {
            return;
        }

        if (TreasureLevelEnum.LOW_LEVEL == treasureLevelEnum) {
            sysConfigService.updateTreasureLow(sysConfigId, alreadyNum);
        } else if (TreasureLevelEnum.HIGHT_LEVEL == treasureLevelEnum) {
            sysConfigService.updateTreasureHeight(sysConfigId, alreadyNum);
        }
        sysConfigService.updateTreasureAmount(sysConfigId, sysAlreadyAmount, amount);
        TreasureDistributionInfo treasureDistributionInfo = treasureDistributionInfoMapper.findById(treasureDistributionId);
        if (treasureDistributionInfo == null) {
            throw new ServiceException("系统数据有更新异常");
        }
        updateTreasureAmount(treasureDistributionInfo.getTreasureId(), treasureAlreadyAmount, amount);
        userDetailService.updateBalanceAmount(userId, amount);
        CashInfo cashInfo = new CashInfo();
        cashInfo.setCashWithdrawal(amount);
        cashInfo.setCompleteTime(DateUtils.now());
        cashInfo.setStatus(StatusEnum.COMPLETE.getStatus());
        cashInfo.setType(BalanceTypeEnum.RED_ENVELOPES.getType());
        cashInfo.setUserId(userId);
        cashInfoService.save(cashInfo);

    }


    private void updateTreasureAmount(Long id, BigDecimal alreadyAmount, BigDecimal amount) {
        Assert.notNull(amount);
        Assert.notNull(id);
        if (alreadyAmount != null) {
            amount = alreadyAmount.add(amount);
        }
        int resultInt = treasureInfoMapper.updateTreasureAmount(id, alreadyAmount, amount);
        if (resultInt != 1) {
            throw new UpdateException("更新失败处理");
        }
    }
}
