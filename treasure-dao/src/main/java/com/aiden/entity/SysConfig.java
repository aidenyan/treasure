package com.aiden.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SysConfig {
    private Long id;

    private BigDecimal treasureTotalAmount;

    private Date treasureEndTime;

    private Integer treasureHightNum;

    private Integer treasureLowNum;

    private Integer treasureEnabled;

    private String smsContent;

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

    public Integer getTreasureEnabled() {
        return treasureEnabled;
    }

    public void setTreasureEnabled(Integer treasureEnabled) {
        this.treasureEnabled = treasureEnabled;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent == null ? null : smsContent.trim();
    }
}