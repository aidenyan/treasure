package com.aiden.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SysConfig {
    private Long id;

    private BigDecimal treasureTotalAmount;

    private Date treasureEndTime;
    private Integer treasureAlreadyHightNum;

    private Integer treasureAlreadyLowNum;
    private Integer treasureHightNum;

    private Integer treasureLowNum;

    private Boolean treasureEnabled;

    private String accessKey;
    private String accessSecret;
    private BigDecimal treasureAlreadyTotalAmount;
    private Boolean smsEnabled;

    public Boolean getSmsEnabled() {
        return smsEnabled;
    }

    public void setSmsEnabled(Boolean smsEnabled) {
        this.smsEnabled = smsEnabled;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public BigDecimal getTreasureAlreadyTotalAmount() {
        return treasureAlreadyTotalAmount;
    }

    public void setTreasureAlreadyTotalAmount(BigDecimal treasureAlreadyTotalAmount) {
        this.treasureAlreadyTotalAmount = treasureAlreadyTotalAmount;
    }

    public Integer getTreasureAlreadyHightNum() {
        return treasureAlreadyHightNum;
    }

    public void setTreasureAlreadyHightNum(Integer treasureAlreadyHightNum) {
        this.treasureAlreadyHightNum = treasureAlreadyHightNum;
    }

    public Integer getTreasureAlreadyLowNum() {
        return treasureAlreadyLowNum;
    }

    public void setTreasureAlreadyLowNum(Integer treasureAlreadyLowNum) {
        this.treasureAlreadyLowNum = treasureAlreadyLowNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTreasureTotalAmount() {
        return treasureTotalAmount;
    }

    public void setTreasureTotalAmount(BigDecimal treasureTotalAmount) {
        this.treasureTotalAmount = treasureTotalAmount;
    }

    public Date getTreasureEndTime() {
        return treasureEndTime;
    }

    public void setTreasureEndTime(Date treasureEndTime) {
        this.treasureEndTime = treasureEndTime;
    }

    public Integer getTreasureHightNum() {
        return treasureHightNum;
    }

    public void setTreasureHightNum(Integer treasureHightNum) {
        this.treasureHightNum = treasureHightNum;
    }

    public Integer getTreasureLowNum() {
        return treasureLowNum;
    }

    public void setTreasureLowNum(Integer treasureLowNum) {
        this.treasureLowNum = treasureLowNum;
    }

    public Boolean getTreasureEnabled() {
        return treasureEnabled;
    }

    public void setTreasureEnabled(Boolean treasureEnabled) {
        this.treasureEnabled = treasureEnabled;
    }


}