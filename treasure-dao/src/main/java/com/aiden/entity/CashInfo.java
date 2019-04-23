package com.aiden.entity;

import java.math.BigDecimal;
import java.util.Date;

public class CashInfo {
    private Long id;
    private Long userId;
    private BigDecimal cashWithdrawal;
    private Integer type;
    private String accountNum;
    private String accountName;
    private Date createdTime;
    private Date completeTime;
    private Integer status;
    private String accountRealName;

    public String getAccountRealName() {
        return accountRealName;
    }

    public void setAccountRealName(String accountRealName) {
        this.accountRealName = accountRealName;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getCashWithdrawal() {
        return cashWithdrawal;
    }

    public void setCashWithdrawal(BigDecimal cashWithdrawal) {
        this.cashWithdrawal = cashWithdrawal;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }
}
