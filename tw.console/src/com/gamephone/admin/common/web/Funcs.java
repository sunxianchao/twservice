/**
 * $Id: Funcs.java,v 1.2 2012/05/04 01:58:04 jiayu.qiu Exp $
 */
package com.gamephone.admin.common.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import com.gamephone.admin.common.Constants;
import com.gamephone.admin.common.to.AdminUserTO;
import com.gamephone.common.type.YesNoType;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * @author qinchao.zhang@downjoy.com
 */
public class Funcs implements Constants {

    public static AdminUserTO getSessionUser(HttpSession session) {
        if(null == session) {
            return null;
        }
        AdminUserTO user=(AdminUserTO)session.getAttribute(SESSION_USER);
        return user;
    }

    public static boolean isAdminUser(AdminUserTO user) {
        return user.getIsadmin() == YesNoType.YES;
    }

    public static String formatDateTime(Date date, String parten) {
        if(null == date) {
            return "";
        }
        SimpleDateFormat format=new SimpleDateFormat(parten);
        return format.format(date);
    }
    
    public static String getBase64(String str){
        if(null==str || str.length()==0){
            return "";
        }
        return Base64.encode(str.getBytes());
    }

}
