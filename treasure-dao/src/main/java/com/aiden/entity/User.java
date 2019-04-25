package com.aiden.entity;

import java.util.Date;

public class User {
    private Long id;

    private String nickName;

    private String mobile;

    private String password;

    private String token;

    private String source;

    private String deviceId;

    private Integer integral;

    private Integer findTreasurePoint;

    private Integer treasurePoint;

    private Integer luckPoin;

    private String invitedCode;

    private Long sendCreated;

    private Boolean isDeleted=false;

    private String verifyCode;
    private Date verifySendDate;
    private Integer sendNum;

    public Integer getSendNum() {
        return sendNum;
    }

    public void setSendNum(Integer sendNum) {
        this.sendNum = sendNum;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Date getVerifySendDate() {
        return verifySendDate;
    }

    public void setVerifySendDate(Date verifySendDate) {
        this.verifySendDate = verifySendDate;
    }

    public Long getSendCreated() {
        return sendCreated;
    }

    public void setSendCreated(Long sendCreated) {
        this.sendCreated = sendCreated;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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
        this.nickName = nickName == null ? null : nickName.trim();
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

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

    public String getInvitedCode() {
        return invitedCode;
    }

    public void setInvitedCode(String invitedCode) {
        this.invitedCode = invitedCode == null ? null : invitedCode.trim();
    }
}