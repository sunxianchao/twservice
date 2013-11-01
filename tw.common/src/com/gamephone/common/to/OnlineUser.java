package com.gamephone.common.to;

import java.util.Date;


public class OnlineUser {
    
    private String token;

    private Integer userId;
    
    private String tid;
    
    private Integer userGameId;

    private String loginAccount;

    private Integer gameId;
    
    private Integer serverId;

    private Date loginDate;
    
    private Date lastActDate;

    
    public String getToken() {
        return token;
    }

    
    public void setToken(String token) {
        this.token=token;
    }

    
    public Integer getUserId() {
        return userId;
    }

    
    public void setUserId(Integer userId) {
        this.userId=userId;
    }

    
    public String getLoginAccount() {
        return loginAccount;
    }

    
    public void setLoginAccount(String loginAccount) {
        this.loginAccount=loginAccount;
    }

    
    public Integer getGameId() {
        return gameId;
    }

    
    public void setGameId(Integer gameId) {
        this.gameId=gameId;
    }


    
    public Date getLoginDate() {
        return loginDate;
    }


    
    public void setLoginDate(Date loginDate) {
        this.loginDate=loginDate;
    }


    
    public Date getLastActDate() {
        return lastActDate;
    }


    
    public void setLastActDate(Date lastActDate) {
        this.lastActDate=lastActDate;
    }


    
    public Integer getServerId() {
        return serverId;
    }


    
    public void setServerId(Integer serverId) {
        this.serverId=serverId;
    }


    
    public Integer getUserGameId() {
        return userGameId;
    }


    
    public void setUserGameId(Integer userGameId) {
        this.userGameId=userGameId;
    }


    
    public String getTid() {
        return tid;
    }

    
    public void setTid(String tid) {
        this.tid=tid;
    }

    

}
