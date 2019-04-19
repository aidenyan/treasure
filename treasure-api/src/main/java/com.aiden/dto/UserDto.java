package com.aiden.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2019/4/19/019.
 */
@ApiModel("用户的基本信息")
public class UserDto {
    @ApiModelProperty("用户的ID")
    private Long id;
    @ApiModelProperty("用户的昵称")
    private String nickName;
    @ApiModelProperty("用户的手机号码")
    private String mobile;
    @ApiModelProperty("用户的token")
    private String token;
    @ApiModelProperty("用户的积分")
    private Integer integral;
    @ApiModelProperty("用户的寻宝点数")
    private Integer findTreasurePoint;
    @ApiModelProperty("用户的挖宝点数")
    private Integer treasurePoint;
    @ApiModelProperty("用户的幸运值")
    private Integer luckPoin;

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getFindTreasurePoint() {
        return findTreasurePoint;
    }

    public void setFindTreasurePoint(Integer findTreasurePoint) {
        this.findTreasurePoint = findTreasurePoint;
    }

    public Integer getTreasurePoint() {
        return treasurePoint;
    }

    public void setTreasurePoint(Integer treasurePoint) {
        this.treasurePoint = treasurePoint;
    }

    public Integer getLuckPoin() {
        return luckPoin;
    }

    public void setLuckPoin(Integer luckPoin) {
        this.luckPoin = luckPoin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
