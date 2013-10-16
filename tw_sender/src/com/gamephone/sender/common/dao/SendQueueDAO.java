/**
 * $Id: SendQueueDAO.java,v 1.2 2012/05/04 09:26:58 jiayu.qiu Exp $
 */
package com.gamephone.sender.common.dao;

import java.util.List;

import com.gamephone.common.to.SendLogTO;

public interface SendQueueDAO {

    public List<SendLogTO> getSendQueueOrders(Integer timeOut, int trheadInd) throws Exception;

    public void updateSendQueueOrder(SendLogTO id) throws Exception;

    public void removeFromSendQueue(int id) throws Exception;
}
