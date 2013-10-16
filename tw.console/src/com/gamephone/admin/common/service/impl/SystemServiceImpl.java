/**
 * $Id: SystemServiceImpl.java,v 1.4 2012/09/06 01:24:46 jiayu.qiu Exp $
 */
package com.gamephone.admin.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamephone.admin.common.Constants;
import com.gamephone.admin.common.dao.AdminUserDAO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.admin.common.service.SystemService;
import com.gamephone.admin.common.to.AdminUserTO;
import com.gamephone.common.type.StatusType;
import com.gamephone.common.util.MessageDigestUtil;

/**
 * @author qinchao.zhang@downjoy.com
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private AdminUserDAO adminUserDAO;

    public AdminUserTO doLogin(String email, String password) throws AdminException {
        AdminUserTO adminUser=adminUserDAO.getAdminUserByEmail(email);
        if(adminUser == null || adminUser.getStatusType() != StatusType.ACTIVE) {
            throw new AdminException("查无此用户！");
        }
        try {
            password=MessageDigestUtil.getMD5(password + Constants.PASSWORD_SALT_KEY);
        } catch(Exception e) {
            e.printStackTrace();
        }
        if(!password.equals(adminUser.getPassword())) {
            throw new AdminException("密码错误！");
        }
        return adminUser;
    }
}
