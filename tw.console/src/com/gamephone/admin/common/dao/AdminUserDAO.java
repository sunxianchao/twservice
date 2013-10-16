/**
 * $Id: AdminUserDAO.java,v 1.3 2012/03/28 02:58:14 xianchao.sun Exp $
 */
package com.gamephone.admin.common.dao;

import com.gamephone.admin.common.criteria.AdminUserSearchCriteriaTO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.admin.common.to.AdminUserTO;
import com.gamephone.common.criteria.SearchPagerModel;

/**
 * @author qinchao.zhang@downjoy.com
 */
public interface AdminUserDAO {

    /**
     * 通过EMAIL取得管理员
     * @param email
     * @return
     * @throws AdminException
     */
    AdminUserTO getAdminUserByEmail(String email) throws AdminException;

    /**
     * 获取管理员列表
     * @param criteriaTO
     * @return
     * @throws AdminException
     */
    SearchPagerModel<AdminUserTO> getAdminUsers(AdminUserSearchCriteriaTO criteriaTO) throws AdminException;

    /**
     * 根据用户ID获取用户信息
     * @param UserId
     * @return UserTO
     * @throws AdminException
     */
    AdminUserTO getAdminUserById(Long userId) throws AdminException;

    /**
     * 增加用户
     * @param userTO
     * @throws AdminException
     */
    void addAdminUser(AdminUserTO userTO) throws AdminException;

    /**
     * 更新用户
     * @param userTO
     * @throws AdminException
     */
    void updateAdminUser(AdminUserTO userTO) throws AdminException;

    /**
     * 删除用户
     * @param UserId
     * @throws AdminException
     */
    void delAdminUser(Long userId) throws AdminException;

    /**
     * 获得用户数量
     * @param criteriaTO
     */
    Integer getAdminUsersCount(AdminUserSearchCriteriaTO criteriaTO) throws AdminException;

}
