/**
 * $Id: SendQueueService.java,v 1.1 2012/04/16 10:32:37 jiayu.qiu Exp $
 */
package com.gamephone.sender.common.service;

import java.util.List;

import com.gamephone.common.to.SendLogTO;

public interface SendQueueService {

    List<SendLogTO> getSendQueueOrders(Integer timeOut, int threadInd) throws Exception;

    boolean sendSpendLog(SendLogTO order) throws Exception;
    
    void addToBadQueue(SendLogTO order) throws Exception;
}
