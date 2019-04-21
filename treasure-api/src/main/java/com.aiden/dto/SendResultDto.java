package com.aiden.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("短信发送金额")
public class SendResultDto {
    @ApiModelProperty("短信发送结果")
    private Boolean sendResult;
    @ApiModelProperty("是否未注册")
    private Boolean reg;

}
