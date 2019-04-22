package com.aiden.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
@Data
@ApiModel("宝藏基本信息")
public class TreasureInfoDto {
    @ApiModelProperty("宝藏ID")
    private Long id;
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
    @ApiModelProperty("中奖的概率，百分制,比如100，表示百分百中奖，1表示百分之一")
    private BigDecimal probability;


}