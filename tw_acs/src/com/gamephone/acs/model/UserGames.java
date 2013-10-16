package com.gamephone.acs.model;

import java.io.Serializable;
import java.util.Date;


public class UserGames implements Serializable{

    private static final long serialVersionUID=1L;

    private Integer id;
    
    private Integer userId;
    
    private Integer gameId;
    
    private Integer gameRoleId;
    
    private Date lastLoginDate;

    private Integer loginCNT;
    
    private Integer payCNT;
    
    private Integer amount;
    
    private Integer payTotalAmount;
    
    private String lastIP;
    
    private String lastUA;
    
    private String imei;
    
    private String macAddress;
    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id=id;
    }

    
    public Integer getUserId() {
        return userId;
    }

    
    public void setUserId(Integer userId) {
        this.userId=userId;
    }

    
    public Integer getGameId() {
        return gameId;
    }

    
    public void setGameId(Integer gameId) {
        this.gameId=gameId;
    }

 

    
    public Integer getGameRoleId() {
        return gameRoleId;
    }


    
    public void setGameRoleId(Integer gameRoleId) {
        this.gameRoleId=gameRoleId;
    }


    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    
    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate=lastLoginDate;
    }

    public Integer getLoginCNT() {
        return loginCNT;
    }

    public void setLoginCNT(Integer loginCNT) {
        this.loginCNT=loginCNT;
    }

    public Integer getPayCNT() {
        return payCNT;
    }


    
    public void setPayCNT(Integer payCNT) {
        this.payCNT=payCNT;
    }


    
    public Integer getPayTotalAmount() {
        return payTotalAmount;
    }


    
    public void setPayTotalAmount(Integer payTotalAmount) {
        this.payTotalAmount=payTotalAmount;
    }


    
    public String getLastIP() {
        return lastIP;
    }


    
    public void setLastIP(String lastIP) {
        this.lastIP=lastIP;
    }


    
    public String getLastUA() {
        return lastUA;
    }


    
    public void setLastUA(String lastUA) {
        this.lastUA=lastUA;
    }


    
    public String getImei() {
        return imei;
    }


    
    public void setImei(String imei) {
        this.imei=imei;
    }
    
    public Integer getAmount() {
        return amount;
    }


    
    public void setAmount(Integer amount) {
        this.amount=amount;
    }


    
    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress=macAddress;
    }
    
    
}
