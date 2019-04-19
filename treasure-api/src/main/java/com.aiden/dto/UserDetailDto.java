package com.aiden.dto;

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
    @ApiModelProperty("生日")
    private Date birthDay;
    @ApiModelProperty("简介")
    private String introduce;
    @ApiModelProperty("头部图片")
    private String headerUrl;

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
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }
}
