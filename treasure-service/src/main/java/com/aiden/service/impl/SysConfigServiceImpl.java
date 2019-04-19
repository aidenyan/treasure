package com.aiden.service.impl;

import com.aiden.entity.SysConfig;
import com.aiden.mapper.SysConfigMapper;
import com.aiden.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
