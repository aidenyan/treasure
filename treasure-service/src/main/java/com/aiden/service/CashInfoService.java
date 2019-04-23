package com.aiden.service;

import com.aiden.base.Page;
import com.aiden.common.enums.BalanceTypeEnum;
import com.aiden.common.enums.StatusEnum;
import com.aiden.entity.CashInfo;

public interface CashInfoService {
    Page<CashInfo, Void> page(Long userId, BalanceTypeEnum type, Integer currentPage, Integer pageSize);

    void save(CashInfo cashInfo);

    void updateStatus(StatusEnum status, Long id);
}
