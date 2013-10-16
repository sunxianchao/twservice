package com.gamephone.acs.model;

import java.util.Date;

import com.gamephone.common.util.DateUtil;


public class ServerLog {

    private Integer id;
    
    private Integer gameRoleId;
    
    private Date loginDate;
    
    private Integer channelId;

    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id=id;
    }

    
    public Integer getGameRoleId() {
        return gameRoleId;
    }

    
    public void setGameRoleId(Integer gameRoleId) {
        this.gameRoleId=gameRoleId;
    }

    
    public Date getLoginDate() {
        return loginDate;
    }

    
    public void setLoginDate(Date loginDate) {
        this.loginDate=loginDate;
    }

    
    public Integer getChannelId() {
        return channelId;
    }

    
    public void setChannelId(Integer channelId) {
        this.channelId=channelId;
    }


    @Override
    public String toString() {
        return this.gameRoleId +"\t"+this.getChannelId()+"\t"+DateUtil.currDays();
    }
    
    
}
