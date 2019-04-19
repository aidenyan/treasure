package com.aiden.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel("宝藏基本信息")
public class TreasureInfoDto {
    @ApiModelProperty("宝藏名称")
    private String treasureName;
    @ApiModelProperty(value = "宝藏等级，0:表示低，1：表示高")
    private Integer level;
    @ApiModelProperty("宝藏范围单位米")
    private BigDecimal distance;
    @ApiModelProperty("1-红包，2-文字，3-语音，4-视频")
    private Integer type;
    @ApiModelProperty("非红包情况下的内容")
    private String content;
    @ApiModelProperty("红包金额")
    private BigDecimal amount;
    @ApiModelProperty("结束时间")
    private Date endTime;
    @ApiModelProperty("中奖的概率，百分制")
    private BigDecimal probability;
    @ApiModelProperty("总的金额")
    private BigDecimal totalAmount;

    @NotBlank
    public String getTreasureName() {
        return treasureName;
    }

    public void setTreasureName(String treasureName) {
        this.treasureName = treasureName == null ? null : treasureName.trim();
    }
    @NotNull
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
    @NotNull
    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }



    @NotNull
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
    @NotNull
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    @NotNull
    public BigDecimal getProbability() {
        return probability;
    }

    public void setProbability(BigDecimal probability) {
        this.probability = probability;
    }
    @NotNull
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

}