/**
 * 
 */
package com.gamephone.admin.common.dao.ibatis;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.gamephone.admin.common.dao.MenuDAO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.admin.common.to.AdminUserMenuTO;
import com.gamephone.admin.common.to.AdminUserTO;
import com.gamephone.admin.common.to.MenuTO;

/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-3-26
 */
public class MenuDAOImpl extends SqlMapClientDaoSupport implements MenuDAO {

    public void add(MenuTO to) throws AdminException {
        try {
            this.getSqlMapClient().insert("MENU.add", to);
        } catch(SQLException e) {
            throw new AdminException(e);
        }
    }

    public void deleteById(Long id) throws AdminException {
        try {
            this.getSqlMapClient().delete("MENU.deleteById", id);
        } catch(SQLException e) {
            throw new AdminException(e);
        }
    }

    public MenuTO getById(Long id) throws AdminException {
        try {
            return (MenuTO)this.getSqlMapClient().queryForObject("MENU.getById", id);
        } catch(SQLException e) {
            throw new AdminException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<MenuTO> getMenus() throws AdminException {
        try {
            return (List<MenuTO>)this.getSqlMapClient().queryForList("MENU.getMenus");
        } catch(SQLException e) {
            throw new AdminException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<MenuTO> getUserMenus(AdminUserTO user) throws AdminException {
        try {
            return (List<MenuTO>)this.getSqlMapClient().queryForList("MENU.getUserMenus", user.getId());
        } catch(SQLException e) {
            throw new AdminException(e);
        }
    }

    public void update(MenuTO to) throws AdminException {
        try {
            this.getSqlMapClient().update("MENU.update", to);
        } catch(SQLException e) {
            throw new AdminException(e);
        }
    }

    public void updateChildParentPath(MenuTO oldMenu, MenuTO newMenu) throws AdminException {
        try {
            String oldLCode=oldMenu.getParentIdPath() + oldMenu.getId() + "$";
            String newLCode=newMenu.getParentIdPath() + newMenu.getId() + "$";
            Map<String, String> params=new HashMap<String, String>();
            params.put("oldParentIdPath", oldLCode);
            params.put("newParentIdPath", newLCode);
            this.getSqlMapClient().update("MENU.updateChildParentPath", params);
        } catch(SQLException e) {
            throw new AdminException(e);
        }
    }

    public void addUserMenu(AdminUserMenuTO userMenuTO) throws AdminException {
        try {
            this.getSqlMapClient().insert("MENU.addUserMenu", userMenuTO);
        } catch(SQLException e) {
            throw new AdminException(e);
        }
    }

    public void deleteUserMenus(AdminUserTO user) throws AdminException {
        try {
            this.getSqlMapClient().delete("MENU.deleteUserMenus", user.getId());
        } catch(SQLException e) {
            throw new AdminException(e);
        }
    }

    public void deleteUserMenusByMenuId(Long menuId) throws AdminException {
        try {
            this.getSqlMapClient().delete("MENU.deleteUserMenusByMenuId", menuId);
        } catch(SQLException e) {
            throw new AdminException(e);
        }
    }

}
