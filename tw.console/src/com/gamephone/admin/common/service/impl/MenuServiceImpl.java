/**
 * 
 */
package com.gamephone.admin.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamephone.admin.common.dao.MenuDAO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.admin.common.service.MenuService;
import com.gamephone.admin.common.to.AdminUserMenuTO;
import com.gamephone.admin.common.to.AdminUserTO;
import com.gamephone.admin.common.to.MenuTO;
import com.gamephone.common.type.YesNoType;

/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-3-26
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDAO menuDAO;

    public List<MenuTO> getUserMenus(AdminUserTO user) throws AdminException {
        if(YesNoType.YES == user.getIsadmin()) {// 如果是超及管理员，可以访问系统所有菜单
            return menuDAO.getMenus();
        } else {
            return menuDAO.getUserMenus(user);
        }
    }

    public List<MenuTO> getMenus() throws AdminException {
        return menuDAO.getMenus();
    }

    public MenuTO getMenuById(Long id) throws AdminException {
        MenuTO to=menuDAO.getById(id);
        Long fid=to.getParent().getId();
        if(fid > 0) {
            to.setParent(menuDAO.getById(fid));
        }
        return to;
    }

    public void saveMenuTO(MenuTO to) throws AdminException {
        if(null == to.getId()) {
            menuDAO.add(to);
        } else {
            MenuTO oldMenu=menuDAO.getById(to.getId());
            menuDAO.update(to);
            if(oldMenu.getParent().getId().intValue() != to.getParent().getId().intValue()) {
                menuDAO.updateChildParentPath(oldMenu, to);
            }
        }
    }

    public void deleteMenu(Long menuId) throws AdminException {
        menuDAO.deleteById(menuId);
        menuDAO.deleteUserMenusByMenuId(menuId);
    }

    public void addUserMenuTOs(AdminUserTO user, List<Long> menuIds) throws AdminException {
        if(null == menuIds || menuIds.isEmpty()) {
            throw new AdminException("请选择要保存的节点！");
        }
        if(user.getIsadmin() == YesNoType.YES) {// 如果是超及管理员不需要保存菜单。
            return;
        }
        menuDAO.deleteUserMenus(user);
        for(Long menuId: menuIds) {
            AdminUserMenuTO to=new AdminUserMenuTO();
            to.setUserId(user.getId());
            to.setMenuId(menuId);
            menuDAO.addUserMenu(to);
        }
    }
}
