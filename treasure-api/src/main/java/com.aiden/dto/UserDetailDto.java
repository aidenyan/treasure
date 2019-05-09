package com.aiden.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by Administrator on 2019/4/19/019.
 */
@ApiModel("用户的详细信息")
public class UserDetailDto {
    @ApiModelProperty("用户的性别0：男的，1：女的")
    private Byte sex;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("生日")
    private Date birthDay;
    @ApiModelProperty("简介")
    private String introduce;
    @ApiModelProperty("头部图片")
    private String headerUrl;
    @ApiModelProperty("是否已经认证")
    private Boolean hasCertification;

    @ApiModelProperty("身份证")
    private String identificationCard;

    private final static String HTTP_URL="http://47.98.237.45:81/";

    public Boolean getHasCertification() {
        return hasCertification;
    }

    public void setHasCertification(Boolean hasCertification) {
        this.hasCertification = hasCertification;
    }

    public String getIdentificationCard() {
        return identificationCard;
    }

    public void setIdentificationCard(String identificationCard) {
        this.identificationCard = identificationCard;
    }

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
}
