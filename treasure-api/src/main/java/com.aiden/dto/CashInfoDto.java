package com.aiden.dto;

import com.aiden.common.enums.BalanceTypeEnum;
import com.aiden.common.enums.StatusEnum;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
@ApiModel("现金的基本信息")
public class CashInfoDto {
    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("金额")
    private BigDecimal cashWithdrawal;
    @ApiModelProperty("类型")
    private BalanceTypeEnum type;
    @ApiModelProperty("账号")
    private String accountNum;
    @ApiModelProperty("账号名称，如中国银行，支付宝")
    private String accountName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createdTime;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("完成时间")
    private Date completeTime;
    @ApiModelProperty("状态")
    private StatusEnum status;

}
