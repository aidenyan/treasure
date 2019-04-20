package com.aiden.service.impl;

import com.aiden.entity.SysConfig;
import com.aiden.exception.UpdateException;
import com.aiden.mapper.SysConfigMapper;
import com.aiden.service.SysConfigService;
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
public class SysConfigServiceImpl implements SysConfigService {
    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public SysConfig findOne() {
        return sysConfigMapper.findOne();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public void updateTreasureLow(Long id, Integer lowNum) {
        Assert.notNull(id);
        int resultInt = sysConfigMapper.updateTreasureLow(id, lowNum);
        if (resultInt != 1) {
            throw new UpdateException("更新失败处理");
        }
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public void updateTreasureHeight(Long id, Integer heightNum) {
        Assert.notNull(id);
        int resultInt = sysConfigMapper.updateTreasureHeight(id, heightNum);
        if (resultInt != 1) {
            throw new UpdateException("更新失败处理");
        }
    }

    @Override
    public void updateTreasureAmount(Long id, BigDecimal alreadyAmount, BigDecimal amount) {
        Assert.notNull(id);
        Assert.notNull(amount);
        BigDecimal newAlreadyAmount=amount;
         if(alreadyAmount!=null){
             newAlreadyAmount=alreadyAmount.add(amount);
         }
        int resultInt = sysConfigMapper.updateTreasureAmount(id, alreadyAmount,newAlreadyAmount);
        if (resultInt != 1) {
            throw new UpdateException("更新失败处理");
        }
    }
}
