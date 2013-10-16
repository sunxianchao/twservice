/**
 * $Id: AdminUserDAOImpl.java,v 1.4 2012/07/25 15:21:23 jiayu.qiu Exp $
 */
package com.gamephone.admin.common.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.gamephone.admin.common.criteria.AdminUserSearchCriteriaTO;
import com.gamephone.admin.common.dao.AdminUserDAO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.admin.common.to.AdminUserTO;
import com.gamephone.common.criteria.SearchPagerModel;

/**
 * @author qinchao.zhang@downjoy.com
 */
public class AdminUserDAOImpl extends SqlMapClientDaoSupport implements AdminUserDAO {

    public AdminUserTO getAdminUserByEmail(String email) throws AdminException {
        AdminUserTO user=(AdminUserTO)getSqlMapClientTemplate().queryForObject("ADMIN_USER.getAdminUserByEmail", email);
        return user;
    }

    @SuppressWarnings("unchecked")
    public SearchPagerModel<AdminUserTO> getAdminUsers(AdminUserSearchCriteriaTO criteriaTO) throws AdminException {
        SearchPagerModel<AdminUserTO> pager=criteriaTO.getPageModel();
        Integer count=(Integer)getSqlMapClientTemplate().queryForObject("ADMIN_USER.getAdminUsersCount", criteriaTO);
        if(null != count && count.intValue() > 0) {
            List<AdminUserTO> result=getSqlMapClientTemplate().queryForList("ADMIN_USER.getAdminUsers", criteriaTO);
            pager.setResultList(result);
            pager.setTotal(count);
        }
        return pager;
    }

    public AdminUserTO getAdminUserById(Long userId) throws AdminException {
        return (AdminUserTO)getSqlMapClientTemplate().queryForObject("ADMIN_USER.getAdminUserById", userId);
    }

    public void addAdminUser(AdminUserTO userTO) throws AdminException {
        getSqlMapClientTemplate().insert("ADMIN_USER.addAdminUser", userTO);
    }

    public void updateAdminUser(AdminUserTO userTO) throws AdminException {
        getSqlMapClientTemplate().update("ADMIN_USER.updateAdminUser", userTO);
    }

    public void delAdminUser(Long userId) throws AdminException {
        getSqlMapClientTemplate().delete("ADMIN_USER.delAdminUser", userId);
    }

    public Integer getAdminUsersCount(AdminUserSearchCriteriaTO criteriaTO) throws AdminException {
        return (Integer)getSqlMapClientTemplate().queryForObject("ADMIN_USER.getAdminUsersCount", criteriaTO);
    }
}
