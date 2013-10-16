/**
 * $Id: MenuService.java,v 1.2 2012/03/28 02:58:14 xianchao.sun Exp $
 */
package com.gamephone.admin.common.service;

import java.util.List;

import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.admin.common.to.AdminUserTO;
import com.gamephone.admin.common.to.MenuTO;

/**
 * @author jiayu.qiu@downjoy.com
 */
public interface MenuService {

    List<MenuTO> getUserMenus(AdminUserTO user) throws AdminException;

    List<MenuTO> getMenus() throws AdminException;

    MenuTO getMenuById(Long id) throws AdminException;

    void saveMenuTO(MenuTO to) throws AdminException;

    void deleteMenu(Long menuId) throws AdminException;

    void addUserMenuTOs(AdminUserTO user, List<Long> menuIds) throws AdminException;
}
