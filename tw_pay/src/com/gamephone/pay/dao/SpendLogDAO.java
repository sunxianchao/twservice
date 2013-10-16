/**
 * $Id: SpendLogDAO.java,v 1.1 2012/09/25 06:07:37 xianchao.sun Exp $
 */
package com.gamephone.pay.dao;


import com.gamephone.common.to.SendLogTO;
import com.gamephone.pay.exception.YunPayException;

public interface SpendLogDAO {

    void addSendQueueTO(SendLogTO to) throws YunPayException;
}
