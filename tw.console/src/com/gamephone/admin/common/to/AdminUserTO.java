/**
 * $Id: AdminUserTO.java,v 1.4 2012/04/16 02:11:10 xianchao.sun Exp $
 */
package com.gamephone.admin.common.to;

import com.gamephone.common.type.StatusType;
import com.gamephone.common.type.YesNoType;

/**
 * @author xianchao.sun@downjoy.com
 * 2013-3-6
 */
public class AdminUserTO {

    private Long id;

    private String email;

    private String realname;

    private String password;

    private YesNoType isadmin;

    private StatusType statusType;
    
    private String gameids;
    
    private String promptids;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname=realname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType=statusType;
    }

    public YesNoType getIsadmin() {
        return isadmin;
    }

    public void setIsadmin(YesNoType isadmin) {
        this.isadmin=isadmin;
    }

    
    public String getGameids() {
        return gameids;
    }

    
    public void setGameids(String gameids) {
        this.gameids=gameids;
    }

    
    public String getPromptids() {
        return promptids;
    }

    
    public void setPromptids(String promptids) {
        this.promptids=promptids;
    }
    
    
}
