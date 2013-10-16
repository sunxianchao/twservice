/**
 * 
 */
package com.gamephone.billing.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gamephone.billing.dao.SendMapper;
import com.gamephone.billing.exception.BillingException;
import com.gamephone.billing.model.SendQueue;
import com.gamephone.billing.service.SendService;


/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-9-12
 */
@Service
public class SendServiceImpl implements SendService {

    @Resource
    private SendMapper sendMapper;

    public void createSendQueue(int type, String logId) throws BillingException {
        SendQueue to=new SendQueue();
        to.setOrderFrom(type);
        to.setOrderId(Integer.valueOf(logId));
        sendMapper.createSendQueue(to);
    }
}
