package com.gamephone.pay.dao.ibatis;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.gamephone.common.ErrorCode;
import com.gamephone.common.to.PayChannelTO;
import com.gamephone.pay.dao.PayChannelDAO;
import com.gamephone.pay.exception.YunPayException;


public class PayChannelDAOImpl extends SqlMapClientDaoSupport implements PayChannelDAO {

    private static final Logger logger=Logger.getLogger(PayChannelDAOImpl.class);

    @Override
    public List<PayChannelTO> getPayChannels() throws YunPayException {
        try {
            return (List<PayChannelTO>)this.getSqlMapClient().queryForList("PAY.getPayChannels");
        } catch(SQLException e) {
            throw new YunPayException(ErrorCode.DB_ERROR, e);
        }
    }

    @Override
    public List<PayChannelTO> getPayServices() throws YunPayException {
        try {
            return (List<PayChannelTO>)this.getSqlMapClient().queryForList("PAY.getPayServices");
        } catch(SQLException e) {
            throw new YunPayException(ErrorCode.DB_ERROR, e);
        }
    }
}
