package com.aiden.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TreasureInfo {
    private Long id;

    private String treasureName;

    private Integer level;

    private BigDecimal distance;

    private Integer type;

    private String content;

    private BigDecimal amount;

    private Date endTime;

    private BigDecimal probability;

    private BigDecimal totalAmount;

    private BigDecimal alreadyAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTreasureName() {
        return treasureName;
    }

    public void setTreasureName(String treasureName) {
        this.treasureName = treasureName == null ? null : treasureName.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getProbability() {
        return probability;
    }

    public void setProbability(BigDecimal probability) {
        this.probability = probability;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getAlreadyAmount() {
        return alreadyAmount;
    }

    public void setAlreadyAmount(BigDecimal alreadyAmount) {
        this.alreadyAmount = alreadyAmount;
    }
}