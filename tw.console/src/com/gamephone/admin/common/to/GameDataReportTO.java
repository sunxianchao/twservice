/**
 * $Id: GameDataReportTO.java,v 1.3 2012/05/21 09:23:18 xianchao.sun Exp $
 */
package com.gamephone.admin.common.to;

import java.util.Date;

/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-4-17 游戏数据报表
 */
public class GameDataReportTO {

    private Long id;

    private Long gameId;

    private Integer maxOnline;

    private Integer totalOnline;

    private Integer requestCNT;

    private Integer loginUser;

    private Integer unionLoginUser;

    private Long gameServerId;

    private String gameServerName;

    private Integer payUser;

    private Integer payaMount;

    private Integer newPayUser;

    private Integer newUser;

    private Date createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public Integer getMaxOnline() {
        return maxOnline;
    }

    public void setMaxOnline(Integer maxOnline) {
        this.maxOnline=maxOnline;
    }

    public Integer getTotalOnline() {
        return totalOnline;
    }

    public void setTotalOnline(Integer totalOnline) {
        this.totalOnline=totalOnline;
    }

    public Integer getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(Integer loginUser) {
        this.loginUser=loginUser;
    }

    public Integer getUnionLoginUser() {
        return unionLoginUser;
    }

    public void setUnionLoginUser(Integer unionLoginUser) {
        this.unionLoginUser=unionLoginUser;
    }

    public Long getGameServerId() {
        return gameServerId;
    }

    public void setGameServerId(Long gameServerId) {
        this.gameServerId=gameServerId;
    }

    public Integer getPayUser() {
        return payUser;
    }

    public void setPayUser(Integer payUser) {
        this.payUser=payUser;
    }

    public Integer getPayaMount() {
        return payaMount;
    }

    public void setPayaMount(Integer payaMount) {
        this.payaMount=payaMount;
    }

    public Integer getNewPayUser() {
        return newPayUser;
    }

    public void setNewPayUser(Integer newPayUser) {
        this.newPayUser=newPayUser;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate=createdDate;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId=gameId;
    }

    public String getGameServerName() {
        return gameServerName;
    }

    public void setGameServerName(String gameServerName) {
        this.gameServerName=gameServerName;
    }

    public Integer getRequestCNT() {
        return requestCNT;
    }

    public void setRequestCNT(Integer requestCNT) {
        this.requestCNT=requestCNT;
    }

    public Integer getNewUser() {
        return newUser;
    }

    public void setNewUser(Integer newUser) {
        this.newUser=newUser;
    }

}
