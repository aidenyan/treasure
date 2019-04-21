package com.aiden.service;

import com.aiden.base.Page;
import com.aiden.entity.CashInfo;

public interface CashInfoService {
    Page<CashInfo,Void> page(Long userId, Integer currentPage, Integer pageSize);
    void save(CashInfo cashInfo);
}
