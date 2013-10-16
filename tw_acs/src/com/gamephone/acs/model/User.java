package com.gamephone.acs.model;

import java.io.Serializable;
import java.util.Date;


public class User implements Serializable{

    private static final long serialVersionUID=1L;

    private Integer id;
    
    /**
     * 第三方用户id
     */
    private String tid;
    
    private String userName;
    
    private String nickName;
    
    private String email;

    private String phoneNum;
    
    private String password;
    
    private String realName;
    
    private String idCardNo;
    
    private Integer referral;
    
    private Integer channelId;
    
    private Integer status;
    
    private Integer gender;
    
    private Date birthday;
    
    private Date signUpDate;
    
    private String avatar;
    
    private Integer registerGameId;
    
    private Integer userType=0;// 第三方用户来源；0：自己的；1fb；2google；

    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id=id;
    }

    
    public String getTid() {
        return tid;
    }

    
    public void setTid(String tid) {
        this.tid=tid;
    }

    
    public String getUserName() {
        return userName;
    }

    
    public void setUserName(String userName) {
        this.userName=userName;
    }

    
    public String getNickName() {
        return nickName;
    }

    
    public void setNickName(String nickName) {
        this.nickName=nickName;
    }

    
    public String getEmail() {
        return email;
    }

    
    public void setEmail(String email) {
        this.email=email;
    }

    
    public String getPhoneNum() {
        return phoneNum;
    }

    
    public void setPhoneNum(String phoneNum) {
        this.phoneNum=phoneNum;
    }

    
    public String getPassword() {
        return password;
    }

    
    public void setPassword(String password) {
        this.password=password;
    }

    
    public String getRealName() {
        return realName;
    }

    
    public void setRealName(String realName) {
        this.realName=realName;
    }

    
    public String getIdCardNo() {
        return idCardNo;
    }

    
    public void setIdCardNo(String idCardNo) {
        this.idCardNo=idCardNo;
    }

    
    public Integer getReferral() {
        return referral;
    }

    
    public void setReferral(Integer referral) {
        this.referral=referral;
    }

    
    public Integer getChannelId() {
        return channelId;
    }

    
    public void setChannelId(Integer channelId) {
        this.channelId=channelId;
    }

    
    public Integer getStatus() {
        return status;
    }

    
    public void setStatus(Integer status) {
        this.status=status;
    }

    
    public Integer getGender() {
        return gender;
    }

    
    public void setGender(Integer gender) {
        this.gender=gender;
    }

    
    public Date getBirthday() {
        return birthday;
    }

    
    public void setBirthday(Date birthday) {
        this.birthday=birthday;
    }

    
    public Date getSignUpDate() {
        return signUpDate;
    }

    
    public void setSignUpDate(Date signUpDate) {
        this.signUpDate=signUpDate;
    }

    
    public String getAvatar() {
        return avatar;
    }

    
    public void setAvatar(String avatar) {
        this.avatar=avatar;
    }

    
    public Integer getRegisterGameId() {
        return registerGameId;
    }

    
    public void setRegisterGameId(Integer registerGameId) {
        this.registerGameId=registerGameId;
    }


    
    public Integer getUserType() {
        return userType;
    }


    
    public void setUserType(Integer userType) {
        this.userType=userType;
    }
    
}
