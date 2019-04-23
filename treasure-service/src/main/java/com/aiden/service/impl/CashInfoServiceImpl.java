package com.aiden.service.impl;

import com.aiden.base.Page;
import com.aiden.common.enums.BalanceTypeEnum;
import com.aiden.common.enums.StatusEnum;
import com.aiden.common.utils.DateUtils;
import com.aiden.entity.CashInfo;
import com.aiden.exception.UpdateException;
import com.aiden.mapper.CashInfoMapper;
import com.aiden.service.CashInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional(readOnly = true)
public class CashInfoServiceImpl implements CashInfoService {
    @Autowired
    private CashInfoMapper cashInfoMapper;
    @Override
    public Page<CashInfo,Void> page(Long userId, BalanceTypeEnum type, Integer currentPage, Integer pageSize) {
        Assert.notNull(userId);
        Assert.notNull(currentPage);
        Assert.notNull(pageSize);
        Integer typeValue=null;
        if(type!=null){
            typeValue=type.getType();
        }
        int total=cashInfoMapper.count(userId,typeValue);
        Page<CashInfo,Void> page=new Page<>();
        page.setTotal(total);
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        if(total==0){
            return page;
        }
        page.setResult(cashInfoMapper.findList(userId,typeValue,(page.getCurrentPage()-1)*page.getPageSize(),page.getPageSize()));
        return page;
    }

    @Override
    @Transactional( rollbackFor = {Exception.class})
    public void save(CashInfo cashInfo) {
        Assert.notNull(cashInfo);
        Assert.notNull(cashInfo.getUserId());
        Assert.notNull(cashInfo.getType());
        Assert.notNull(cashInfo.getCashWithdrawal());
        cashInfo.setCreatedTime(DateUtils.now());
        cashInfoMapper.insert(cashInfo);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public void updateStatus(StatusEnum status, Long id) {
        Assert.notNull(status);
        Assert.notNull(id);
        int result= cashInfoMapper.updateStatus(status.getStatus(),id);
        if (result != 1) {
            throw new UpdateException("更新失败处理");
        }
    }
}
