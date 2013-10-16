/**
 * $Id: StatusTypeHandler.java,v 1.1 2012/03/23 11:46:32 xianchao.sun Exp $
 */
package com.gamephone.admin.common.dao.ibatis.typehandler;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import com.gamephone.common.type.StatusType;

/**
 * @author jiayu.qiu@downjoy.com
 */
public class StatusTypeHandler implements TypeHandlerCallback {

    public Object getResult(ResultGetter paramResultGetter) throws SQLException {
        return StatusType.getById(paramResultGetter.getInt());
    }

    public void setParameter(ParameterSetter paramParameterSetter, Object paramObject) throws SQLException {
        paramParameterSetter.setInt(((paramObject == null) ? null : (StatusType)paramObject).getId().intValue());
    }

    public Object valueOf(String paramString) {
        if((paramString == null) || (paramString.length() == 0))
            return null;
        return StatusType.getById(Integer.valueOf(paramString));
    }
}
