package com.aiden.service.impl;

import com.aiden.common.utils.MobileUtils;
import com.aiden.service.MobileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/19/019.
 */
@Service
@Transactional(readOnly = true)
public class MobileServiceImpl implements MobileService {
    @Override
    public boolean sendMobile(String mobile, String content) {
        if (!MobileUtils.isPhone(mobile)) {
            return false;
        }
        return true;
    }
}
