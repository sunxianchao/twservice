/**
 * $Id: BadQueueDAOImpl.java,v 1.1 2012/04/16 10:32:37 jiayu.qiu Exp $
 */
package com.gamephone.sender.common.dao.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.gamephone.common.to.SendLogTO;
import com.gamephone.sender.common.dao.BadQueueDAO;

public class BadQueueDAOImpl extends SqlMapClientDaoSupport implements BadQueueDAO {

    public void addBadQueue(SendLogTO sendQueueTO) throws Exception {
        this.getSqlMapClientTemplate().insert("BAD_QUEUE.addBadQueue", sendQueueTO);
    }
}
