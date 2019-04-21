package com.aiden.dto;

import com.aiden.common.enums.BalanceTypeEnum;
import com.aiden.common.enums.StatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class CashInfoDto {
    private Long id;
    private Long userId;
    private BigDecimal cashWithdrawal;
    private BalanceTypeEnum type;
    private String accountNum;
    private String accountName;
    private Date createdTime;
    private Date completeTime;
    private StatusEnum status;

}
