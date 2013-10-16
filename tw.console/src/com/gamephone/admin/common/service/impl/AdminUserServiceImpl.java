/**
 * $Id: AdminUserServiceImpl.java,v 1.3 2012/07/13 07:33:02 jiayu.qiu Exp $
 */
package com.gamephone.admin.common.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamephone.admin.common.criteria.AdminUserSearchCriteriaTO;
import com.gamephone.admin.common.dao.AdminUserDAO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.admin.common.service.AdminUserService;
import com.gamephone.admin.common.to.AdminUserTO;
import com.gamephone.common.criteria.SearchPagerModel;

/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-3-26
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserDAO adminUserDAO;

    public SearchPagerModel<AdminUserTO> getAdminUsers(AdminUserSearchCriteriaTO criteriaTO) throws AdminException {
        return adminUserDAO.getAdminUsers(criteriaTO);
    }

    public void delAdminUser(Long userId) throws AdminException {
        adminUserDAO.delAdminUser(userId);
    }

    public AdminUserTO getAdminUserById(Long userId) throws AdminException {
        return adminUserDAO.getAdminUserById(userId);
    }

    public AdminUserTO getAdminUserByEmail(String email) throws AdminException {
        return adminUserDAO.getAdminUserByEmail(email);
    }

    public void addAdminUser(AdminUserTO userTO) throws AdminException {
        adminUserDAO.addAdminUser(userTO);
    }

    public void updateAdminUser(AdminUserTO userTO) throws AdminException {
        adminUserDAO.updateAdminUser(userTO);
    }

    public Integer getAdminUsersCount(AdminUserSearchCriteriaTO criteriaTO) throws AdminException {
        return adminUserDAO.getAdminUsersCount(criteriaTO);
    }
}
