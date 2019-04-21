package com.aiden.service.impl;

import com.aiden.base.Page;
import com.aiden.common.utils.DateUtils;
import com.aiden.entity.CashInfo;
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
    public Page<CashInfo,Void> page(Long userId, Integer currentPage, Integer pageSize) {
        Assert.notNull(userId);
        Assert.notNull(currentPage);
        Assert.notNull(pageSize);
        int total=cashInfoMapper.count(userId);
        Page<CashInfo,Void> page=new Page<>();
        page.setTotal(total);
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        if(total==0){
            return page;
        }
        page.setResult(cashInfoMapper.findList(userId,(page.getCurrentPage()-1)*page.getPageSize(),page.getPageSize()));

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
}
