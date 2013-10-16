/**
 * $Id: MenuDAO.java,v 1.2 2012/03/28 02:58:14 xianchao.sun Exp $
 */
package com.gamephone.admin.common.dao;

import java.util.List;

import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.admin.common.to.AdminUserMenuTO;
import com.gamephone.admin.common.to.AdminUserTO;
import com.gamephone.admin.common.to.MenuTO;

/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-3-26
 */
public interface MenuDAO {

    List<MenuTO> getMenus() throws AdminException;

    List<MenuTO> getUserMenus(AdminUserTO user) throws AdminException;

    MenuTO getById(Long id) throws AdminException;

    void add(MenuTO to) throws AdminException;

    void deleteById(Long id) throws AdminException;

    void update(MenuTO to) throws AdminException;

    /**
     * 当菜单更新父亲节点时,更新其子菜单的MENU_LCODE
     * @param oldMenu
     * @param newMenu
     * @throws AdminException
     */
    void updateChildParentPath(MenuTO oldMenu, MenuTO newMenu) throws AdminException;

    void addUserMenu(AdminUserMenuTO userMenuTO) throws AdminException;

    /**
     * @param user
     * @throws AdminException
     */
    void deleteUserMenus(AdminUserTO user) throws AdminException;

    void deleteUserMenusByMenuId(Long menuId) throws AdminException;
}
