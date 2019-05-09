package com.aiden.dto;

import com.aiden.common.enums.TreasureLevelEnum;
import com.aiden.common.enums.TreasureTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("宝藏打开信息")
public class TreasureOpenResultDto {
    @ApiModelProperty("打开宝藏信息结果，true:中奖，false:未中奖")
    private Boolean result;
    @ApiModelProperty("宝藏名字")
    private String treasureName;
    @ApiModelProperty("宝藏等级")
    private TreasureLevelEnum level;

    @ApiModelProperty("宝藏类型")
    private TreasureTypeEnum type;
    @ApiModelProperty("宝藏内容")
    private String content;
    @ApiModelProperty("宝藏金额")
    private BigDecimal amount;

}
