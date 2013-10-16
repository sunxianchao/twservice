/**
 * 
 */
package com.gamephone.pay.service;


import com.gamephone.pay.exception.YunPayException;

/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-9-12
 */
public interface SpendService {

    public void addSendQueue(int type, String logId) throws YunPayException;
}
