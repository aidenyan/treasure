package com.aiden.service.impl;

import com.aiden.entity.TreasureInfo;
import com.aiden.mapper.TreasureInfoMapper;
import com.aiden.service.TreasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2019/4/19/019.
 */
@Service
@Transactional(readOnly = true)
public class TreasureServiceImpl implements TreasureService {

    @Autowired
    private TreasureInfoMapper treasureInfoMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void saveTreasureInfo(TreasureInfo info) {
        if (info.getId() != null) {
            treasureInfoMapper.update(info);
        } else {
            treasureInfoMapper.insert(info);
        }
    }
}
