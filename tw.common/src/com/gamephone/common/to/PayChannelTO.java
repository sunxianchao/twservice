package com.gamephone.common.to;

public class PayChannelTO{

    private Integer id;

    private Integer gameId;
    
    private String gameName;

    private String payName;

    private String serverId;

    private String serverName;

    private int amount;

    private Integer pid;

    private Integer isLocal;

    private Integer order;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId=gameId;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName=payName;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId=serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName=serverName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount=amount;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid=pid;
    }

    public Integer getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(Integer isLocal) {
        this.isLocal=isLocal;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order=order;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status=status;
    }

    
    public String getGameName() {
        return gameName;
    }

    
    public void setGameName(String gameName) {
        this.gameName=gameName;
    }
    

}
