/**
 * $Id: AdminUserMenuTO.java,v 1.2 2012/03/28 02:58:13 xianchao.sun Exp $
 */
package com.gamephone.admin.common.to;

/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-3-23
 */
public class AdminUserMenuTO {

    private Long userId;

    private Long menuId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId=userId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId=menuId;
    }
}
