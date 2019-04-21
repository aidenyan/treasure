package com.aiden.entity;

import java.math.BigDecimal;

public class UnreceiveTreasure {
    private String treasureName;
    private Integer level;
    private BigDecimal distance;
    private Integer type;
    private BigDecimal lat;
    private BigDecimal lng;
    private Long treasureId;
    private Long treasureDistributionId;
    private BigDecimal juli;

    public String getTreasureName() {
        return treasureName;
    }

    public void setTreasureName(String treasureName) {
        this.treasureName = treasureName;
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

    public Long getTreasureId() {
        return treasureId;
    }

    public void setTreasureId(Long treasureId) {
        this.treasureId = treasureId;
    }

    public Long getTreasureDistributionId() {
        return treasureDistributionId;
    }

    public void setTreasureDistributionId(Long treasureDistributionId) {
        this.treasureDistributionId = treasureDistributionId;
    }

    public BigDecimal getJuli() {
        return juli;
    }

    public void setJuli(BigDecimal juli) {
        this.juli = juli;
    }
}
