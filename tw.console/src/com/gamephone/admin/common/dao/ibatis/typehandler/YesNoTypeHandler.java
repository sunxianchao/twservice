/**
 * $Id: YesNoTypeHandler.java,v 1.1 2012/03/26 07:11:36 qinchao.zhang Exp $
 */
package com.gamephone.admin.common.dao.ibatis.typehandler;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import com.gamephone.common.type.YesNoType;
/**
 * @author qinchao.zhang@downjoy.com
 */
public class YesNoTypeHandler implements TypeHandlerCallback {

    public Object getResult(ResultGetter paramResultGetter) throws SQLException {
        return YesNoType.getById(paramResultGetter.getInt());
    }

    public void setParameter(ParameterSetter paramParameterSetter, Object paramObject) throws SQLException {
        paramParameterSetter.setInt(((paramObject == null) ? null : (YesNoType)paramObject).getId().intValue());
    }

    public Object valueOf(String paramString) {
        if((paramString == null) || (paramString.length() == 0))
            return null;
        return YesNoType.getById(Integer.valueOf(paramString));
    }

}
