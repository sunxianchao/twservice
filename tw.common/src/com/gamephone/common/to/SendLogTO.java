package com.gamephone.common.to;

import java.util.Date;


public class SendLogTO {
    private Integer id;

    private Integer orderId;

    private String postUrl;

    private String params;

    private Date lastSendTime;

    private Integer sendTimes;

    private String sendRes;

    private Integer orderFrom;

    public Date getLastSendTime() {
        return lastSendTime;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public Integer getSendTimes() {
        return sendTimes;
    }

    public void setLastSendTime(Date lastSendTime) {
        this.lastSendTime=lastSendTime;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl=postUrl;
    }

    public void setSendTimes(Integer sendTimes) {
        this.sendTimes=sendTimes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId=orderId;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params=params;
    }

    public String getSendRes() {
        return sendRes;
    }

    public void setSendRes(String sendRes) {
        this.sendRes=sendRes;
    }

    public Integer getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(Integer orderFrom) {
        this.orderFrom=orderFrom;
    }

}
