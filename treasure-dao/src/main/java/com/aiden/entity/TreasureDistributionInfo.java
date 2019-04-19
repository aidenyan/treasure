package com.aiden.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TreasureDistributionInfo {
    private Long id;

    private Long treasureId;

    private BigDecimal lat;

    private BigDecimal lng;

    private Long userId;

    private Byte isReceive;

    private BigDecimal amount;

    private String content;

    private Byte createType;

    private Date createdTime;

    private Long createId;

    private Date receiveTime;

    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTreasureId() {
        return treasureId;
    }

    public void setTreasureId(Long treasureId) {
        this.treasureId = treasureId;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getIsReceive() {
        return isReceive;
    }

    public void setIsReceive(Byte isReceive) {
        this.isReceive = isReceive;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Byte getCreateType() {
        return createType;
    }

    public void setCreateType(Byte createType) {
        this.createType = createType;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}