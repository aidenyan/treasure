package com.aiden.dto;

import com.aiden.common.enums.TreasureLevelEnum;
import com.aiden.common.enums.TreasureTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2019/4/22/022.
 */
@ApiModel("用户的详细信息")
public class UserTreasureDto {
    @ApiModelProperty("地址信息")
    private String address;
    @ApiModelProperty("宝藏金额")
    private BigDecimal amount;
    @ApiModelProperty("是否中奖")
    private Boolean isReceive;
    @ApiModelProperty("宝藏的名字")
    private String treasureName;
    @ApiModelProperty("宝藏类型")
    private TreasureTypeEnum type;
    @ApiModelProperty("宝藏级别")
    private TreasureLevelEnum level;
    @ApiModelProperty("抽奖时间")
    private Date receiveTime;
    @ApiModelProperty("宝藏的内容")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getIsReceive() {
        return isReceive;
    }

    public void setIsReceive(Boolean isReceive) {
        this.isReceive = isReceive;
    }

    public String getTreasureName() {
        return treasureName;
    }

    public void setTreasureName(String treasureName) {
        this.treasureName = treasureName;
    }

    public TreasureTypeEnum getType() {
        return type;
    }

    public void setType(TreasureTypeEnum type) {
        this.type = type;
    }

    public TreasureLevelEnum getLevel() {
        return level;
    }

    public void setLevel(TreasureLevelEnum level) {
        this.level = level;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }
}
