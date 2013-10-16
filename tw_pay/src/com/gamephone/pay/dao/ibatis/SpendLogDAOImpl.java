package com.gamephone.pay.dao.ibatis;

import java.sql.SQLException;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;


import com.gamephone.common.ErrorCode;
import com.gamephone.common.to.SendLogTO;
import com.gamephone.pay.dao.SpendLogDAO;
import com.gamephone.pay.exception.YunPayException;

/**
 * @author xianchao.sun@downjoy.com
 */
public class SpendLogDAOImpl extends SqlMapClientDaoSupport implements SpendLogDAO {

    public void addSendQueueTO(SendLogTO to) throws YunPayException {
        try {
            this.getSqlMapClient().insert("SEND.addSendQueueTO", to);
        } catch(SQLException e) {
            throw new YunPayException(ErrorCode.DB_ERROR, e);
        }
    }
}
