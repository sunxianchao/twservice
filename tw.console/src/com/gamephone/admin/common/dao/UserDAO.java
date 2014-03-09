/**
 * $Id: AdminUserDAO.java,v 1.3 2012/03/28 02:58:14 xianchao.sun Exp $
 */
package com.gamephone.admin.common.dao;

import com.gamephone.admin.common.criteria.UserCriteriaTO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.User;

/**
 * @author qinchao.zhang@downjoy.com
 */
public interface UserDAO {

    /**
     * 获取管理员列表
     * @param criteriaTO
     * @return
     * @throws AdminException
     */
    SearchPagerModel<User> getUsers(UserCriteriaTO criteriaTO) throws AdminException;


    /**
     * 获得用户数量
     * @param criteriaTO
     */
    Integer getUsersCount(UserCriteriaTO criteriaTO) throws AdminException;

}
