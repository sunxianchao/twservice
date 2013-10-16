/**
 * 
 */
package com.gamephone.pay.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gamephone.common.to.SendLogTO;
import com.gamephone.pay.dao.SpendLogDAO;
import com.gamephone.pay.exception.YunPayException;
import com.gamephone.pay.service.SpendService;


/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-9-12
 */
@Service
public class SpendServiceImpl implements SpendService {

    @Resource
    private SpendLogDAO spendLogDAO;

    public void addSendQueue(int type, String logId) throws YunPayException {
        SendLogTO to=new SendLogTO();
        to.setOrderFrom(type);
        to.setOrderId(Integer.valueOf(logId));
        spendLogDAO.addSendQueueTO(to);
    }
}
