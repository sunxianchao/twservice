/**
 * $Id: SendQueueDAOImpl.java,v 1.2 2012/05/04 09:26:58 jiayu.qiu Exp $
 */
package com.gamephone.sender.common.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.gamephone.common.to.SendLogTO;
import com.gamephone.sender.common.dao.SendQueueDAO;

public class SendQueueDAOImpl extends SqlMapClientDaoSupport implements SendQueueDAO {

    @SuppressWarnings("unchecked")
    public List<SendLogTO> getSendQueueOrders(Integer timeOut, int trheadInd) throws Exception {
        Map<String, Integer> params=new HashMap<String, Integer>();
        params.put("timeOut", timeOut);
        params.put("trheadInd", trheadInd);
        return this.getSqlMapClientTemplate().queryForList("SEND_QUEUE.getSendQueueOrders", params);
    }

    public void updateSendQueueOrder(SendLogTO to) throws Exception {
        this.getSqlMapClientTemplate().update("SEND_QUEUE.updateSendQueueOrder", to);
    }

    public void removeFromSendQueue(int id) throws Exception {
        this.getSqlMapClientTemplate().delete("SEND_QUEUE.removeFromSendQueue", id);
    }
}
