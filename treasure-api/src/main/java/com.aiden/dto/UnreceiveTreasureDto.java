package com.aiden.dto;

import com.aiden.common.enums.TreasureLevelEnum;
import com.aiden.common.enums.TreasureTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
@ApiModel("未挖的宝藏信息")
public class UnreceiveTreasureDto {
    @ApiModelProperty("宝藏名称")
    private String treasureName;
    @ApiModelProperty("宝藏等级")
    private TreasureLevelEnum level;
    @ApiModelProperty("宝藏可以挖取的距离")
    private BigDecimal distance;
    @ApiModelProperty("宝藏类型")
    private TreasureTypeEnum type;
    @ApiModelProperty("宝藏位置的纬度")
    private BigDecimal lat;
    @ApiModelProperty("宝藏位置的经度")
    private BigDecimal lng;
    @ApiModelProperty("宝藏类型的ID")
    private Long treasureId;
    @ApiModelProperty("宝藏信息ID")
    private Long treasureDistributionId;


}
