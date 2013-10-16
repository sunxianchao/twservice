/**
 * $Id: BadQueueDAO.java,v 1.1 2012/04/16 10:32:37 jiayu.qiu Exp $
 */
package com.gamephone.sender.common.dao;

import com.gamephone.common.to.SendLogTO;

public interface BadQueueDAO {

    public void addBadQueue(SendLogTO sendQueueTO) throws Exception;
}
