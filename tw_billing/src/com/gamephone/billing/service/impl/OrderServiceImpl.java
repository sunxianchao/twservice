/**
 * 
 */
package com.gamephone.billing.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamephone.billing.dao.OrderMapper;
import com.gamephone.billing.exception.BillingException;
import com.gamephone.billing.model.Order;
import com.gamephone.billing.service.OrderService;
import com.gamephone.billing.service.SendService;

/**
 * @author xianchao.sun@downjoy.com 2012-9-20
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SendService sendService;

    @Override
    public void createOrder(Order order) throws BillingException {
        orderMapper.createOrder(order);
    }

    @Override
    public boolean updateOrderAndSendQueue(Order order) throws BillingException {
        int isSuccess=orderMapper.updateOrder(order);
        if(isSuccess>0){
            sendService.createSendQueue(1, String.valueOf(order.getId()));
        }
        return isSuccess>0;
    }
    
    public boolean updateOrder(Order order) throws BillingException {
        int isSuccess=orderMapper.updateOrder(order);
        return isSuccess>0;
    }
    

    @Override
    public Order getOrderById(Integer id) throws BillingException {
        return orderMapper.getOrderById(id);
    }

    @Override
    public Order getOrderByOrderId(String orderid) throws BillingException {
        return orderMapper.getOrderByOrderId(orderid);
    }

    @Override
    public Order getOrderByAuthCode(String authCode) throws BillingException {
        return orderMapper.getOrderByAuthCode(authCode);
    }

    @Override
    public Order getOrderByTranNo(String tranno, Integer channelId) throws BillingException {
        return orderMapper.getOrderByTranNo(tranno, channelId);
    }
 
}
