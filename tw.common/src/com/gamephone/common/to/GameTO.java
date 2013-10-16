package com.gamephone.common.to;

import java.util.Date;


public class GameTO {

    private Integer id;
    
    private Integer cpId;
    
    private Integer seqNum;
    
    private String name;
    
    private Integer status;
    
    private String secretKey;
    
    private String notifyKey;
    
    private String notifyUrl;
    
    private Date createdDate;

    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id2) {
        this.id=id2;
    }

    
    public Integer getCpId() {
        return cpId;
    }

    
    public void setCpId(Integer cpId) {
        this.cpId=cpId;
    }

    
    public Integer getSeqNum() {
        return seqNum;
    }

    
    public void setSeqNum(Integer seqNum) {
        this.seqNum=seqNum;
    }

    
    public Integer getStatus() {
        return status;
    }

    
    public void setStatus(Integer status) {
        this.status=status;
    }

    
    public String getSecretKey() {
        return secretKey;
    }

    
    public void setSecretKey(String secretKey) {
        this.secretKey=secretKey;
    }

    
    public String getNotifyKey() {
        return notifyKey;
    }

    
    public void setNotifyKey(String notifyKey) {
        this.notifyKey=notifyKey;
    }

    
    public String getNotifyUrl() {
        return notifyUrl;
    }

    
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl=notifyUrl;
    }

    
    public Date getCreatedDate() {
        return createdDate;
    }

    
    public void setCreatedDate(Date createdDate) {
        this.createdDate=createdDate;
    }


    
    public String getName() {
        return name;
    }


    
    public void setName(String name) {
        this.name=name;
    }
    
    
}
