/**
 * $Id: SystemService.java,v 1.1 2012/03/26 07:11:35 qinchao.zhang Exp $
 */
package com.gamephone.admin.common.service;

import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.admin.common.to.AdminUserTO;

/**
 * @author qinchao.zhang@downjoy.com
 */
public interface SystemService {

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return
     * @throws AdminException
     */
    public AdminUserTO doLogin(String username, String password) throws AdminException;
}
