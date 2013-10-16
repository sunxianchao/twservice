/**
 * $Id: Constants.java,v 1.5 2012/03/30 03:39:01 xianchao.sun Exp $
 */
package com.gamephone.admin.common;

import com.gamephone.admin.common.web.JSONView;

/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-3-23
 */
public interface Constants {

    String SESSION_USER="SESSION_USER";

    String JSON_ROOT="JSON_ROOT";

    JSONView JSON_VIEW=new JSONView();
    
    Integer PAGESIZE=20;
    
    String PASSWORD_SALT_KEY="Salt.d.cn.smart.ng.admin.2012"; // md5(用户密码+PASSWORD_SALT_KEY)保存到数据库中。
    
}
