package com.aiden.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2019/4/19/019.
 */
@ApiModel("用户的详细信息")
public class UserDetailDto {
    @ApiModelProperty("用户的性别0：男的，1：女的")
    private Byte sex;
    @ApiModelProperty("生日")
    private Date birthDay;
    @ApiModelProperty("简介")
    private String introduce;
    @ApiModelProperty("头部图片")
    private String headerUrl;
    @ApiModelProperty("余额")
    private BigDecimal balanceAmount;
    @ApiModelProperty("提现余额")
    private BigDecimal alreadyAmount;
    @ApiModelProperty("提现账户名字如支付宝")
    private String accountName;
    @ApiModelProperty("提现账户")
    private String accountNum;
    @ApiModelProperty("用户真实名字")
    private String accountRealName;
    @ApiModelProperty("y邀请码")
    private String invitationCode;

    private final static String HTTP_URL="http://47.98.237.45:81/";

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getHeaderUrl() {
        if(headerUrl!=null&&!headerUrl.trim().startsWith("http")){
            return HTTP_URL+headerUrl;
        }
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public BigDecimal getAlreadyAmount() {
        return alreadyAmount;
    }

    public void setAlreadyAmount(BigDecimal alreadyAmount) {
        this.alreadyAmount = alreadyAmount;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getAccountRealName() {
        return accountRealName;
    }

    public void setAccountRealName(String accountRealName) {
        this.accountRealName = accountRealName;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }
}
