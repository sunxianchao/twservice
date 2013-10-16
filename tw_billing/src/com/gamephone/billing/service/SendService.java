/**
 * 
 */
package com.gamephone.billing.service;

import com.gamephone.billing.exception.BillingException;


/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-9-12
 */
public interface SendService {

    public void createSendQueue(int type, String logId) throws BillingException;
}
