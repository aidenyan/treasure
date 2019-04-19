package com.aiden.entity;

import java.util.Date;

public class UserDetail {
    private Long id;

    private Byte sex;

    private Date birthDay;

    private String introduce;

    private String headerUrl;

    private Byte hasCertification;

    private String identificationCard;

    private String frontIdCardUrl;

    private String reverseIdCardUrl;

    private String invitationCode;

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        this.introduce = introduce == null ? null : introduce.trim();
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl == null ? null : headerUrl.trim();
    }

    public Byte getHasCertification() {
        return hasCertification;
    }

    public void setHasCertification(Byte hasCertification) {
        this.hasCertification = hasCertification;
    }

    public String getIdentificationCard() {
        return identificationCard;
    }

    public void setIdentificationCard(String identificationCard) {
        this.identificationCard = identificationCard == null ? null : identificationCard.trim();
    }

    public String getFrontIdCardUrl() {
        return frontIdCardUrl;
    }

    public void setFrontIdCardUrl(String frontIdCardUrl) {
        this.frontIdCardUrl = frontIdCardUrl == null ? null : frontIdCardUrl.trim();
    }

    public String getReverseIdCardUrl() {
        return reverseIdCardUrl;
    }

    public void setReverseIdCardUrl(String reverseIdCardUrl) {
        this.reverseIdCardUrl = reverseIdCardUrl == null ? null : reverseIdCardUrl.trim();
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode == null ? null : invitationCode.trim();
    }
}