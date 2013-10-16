package com.gamephone.billing.model;

import java.util.Date;

import com.gamephone.common.type.PayTyper;


/**
 * @author xianchao.sun@downjoy.com
 * 2013-3-26
 */
public class Order {
 
    private Integer id;
    
    private Integer userId;
    
    private Integer cpId;
    
    private Integer gameId;
    
    private String gameName;
    
    private Integer gameServerId;
    
    private String cardNo;
    
    private String cardPwd;
    
    private int amount;
    
    private Integer paySuccess;
    
    private String orderId;
    
    private String tradeNo;
    
    private String resultCode;
    
    private String resultMsg;
    
    private Date resultDate;
    
    private Date createdDate;
    
    private String extInfo;
    
    private String notifyStatus;
    
    private Date notifyDate;
    
    private String authCode;
    
    private String productId;
    
    private String proNo;
    
    private PayTyper payType;// 1:ingame;2:billing;3google

    private Integer type;
    
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

    
    public Integer getCpId() {
        return cpId;
    }

    
    public void setCpId(Integer cpId) {
        this.cpId=cpId;
    }

    
    public Integer getGameId() {
        return gameId;
    }

    
    public void setGameId(Integer gameId) {
        this.gameId=gameId;
    }

    
    public Integer getGameServerId() {
        return gameServerId;
    }

    
    public void setGameServerId(Integer gameServerId) {
        this.gameServerId=gameServerId;
    }

    
    public String getCardNo() {
        return cardNo;
    }

    
    public void setCardNo(String cardNo) {
        this.cardNo=cardNo;
    }

    
    public String getCardPwd() {
        return cardPwd;
    }

    
    public void setCardPwd(String cardPwd) {
        this.cardPwd=cardPwd;
    }

    
    public int getAmount() {
        return amount;
    }

    
    public void setAmount(int amount) {
        this.amount=amount;
    }

    
    public Integer getPaySuccess() {
        return paySuccess;
    }

    
    public void setPaySuccess(Integer paySuccess) {
        this.paySuccess=paySuccess;
    }

    
    public String getOrderId() {
        return orderId;
    }

    
    public void setOrderId(String orderId) {
        this.orderId=orderId;
    }

    
    public String getTradeNo() {
        return tradeNo;
    }

    
    public void setTradeNo(String tradeNo) {
        this.tradeNo=tradeNo;
    }

    
    public String getResultCode() {
        return resultCode;
    }

    
    public void setResultCode(String resultCode) {
        this.resultCode=resultCode;
    }

    
    public String getResultMsg() {
        return resultMsg;
    }

    
    public void setResultMsg(String resultMsg) {
        this.resultMsg=resultMsg;
    }

    
    public Date getResultDate() {
        return resultDate;
    }

    
    public void setResultDate(Date resultDate) {
        this.resultDate=resultDate;
    }

    
    public Date getCreatedDate() {
        return createdDate;
    }

    
    public void setCreatedDate(Date createdDate) {
        this.createdDate=createdDate;
    }

    
    public String getExtInfo() {
        return extInfo;
    }

    
    public void setExtInfo(String extInfo) {
        this.extInfo=extInfo;
    }

    
    public String getNotifyStatus() {
        return notifyStatus;
    }

    
    public void setNotifyStatus(String notifyStatus) {
        this.notifyStatus=notifyStatus;
    }

    
    public Date getNotifyDate() {
        return notifyDate;
    }

    
    public void setNotifyDate(Date notifyDate) {
        this.notifyDate=notifyDate;
    }

    
    public String getAuthCode() {
        return authCode;
    }

    
    public void setAuthCode(String authCode) {
        this.authCode=authCode;
    }

    
    public String getProductId() {
        return productId;
    }

    
    public void setProductId(String productId) {
        this.productId=productId;
    }

    
    public String getProNo() {
        return proNo;
    }


    
    public void setProNo(String proNo) {
        this.proNo=proNo;
    }


    
    public String getGameName() {
        return gameName;
    }


    
    public void setGameName(String gameName) {
        this.gameName=gameName;
    }


    
    public PayTyper getPayType() {
        return payType;
    }


    
    public void setPayType(PayTyper payType) {
        this.payType=payType;
    }


    
    public void setType(Integer type) {
        this.type=type;
    }
    
    public Integer getType(){
        return this.type;
    }
    
}
