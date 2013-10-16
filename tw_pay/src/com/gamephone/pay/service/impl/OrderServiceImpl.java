/**
 * 
 */
package com.gamephone.pay.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gamephone.common.to.OrderTO;
import com.gamephone.pay.criteria.OrderCriteriaTO;
import com.gamephone.pay.dao.OrderDAO;
import com.gamephone.pay.exception.YunPayException;
import com.gamephone.pay.service.OrderService;
import com.gamephone.pay.service.SpendService;

/**
 * @author xianchao.sun@downjoy.com 2012-9-20
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDAO otherOrderDAO;

    @Resource
    private SpendService spendService;

    @Override
    public void addOrder(OrderTO order) throws YunPayException {
        otherOrderDAO.addOrder(order);
    }

    @Override
    public boolean updateOrderAndSendQueue(OrderTO order) throws YunPayException {
        boolean isSuccess=otherOrderDAO.updateOrder(order);
        if(isSuccess){
            spendService.addSendQueue(1, String.valueOf(order.getId()));
        }
        return isSuccess;
    }
    
    public boolean updateOrder(OrderTO order) throws YunPayException {
        boolean isSuccess=otherOrderDAO.updateOrder(order);
        return isSuccess;
    }
    

    @Override
    public OrderTO getOrderById(Integer id) throws YunPayException {
        return otherOrderDAO.getOrderById(id);
    }

    @Override
    public OrderTO getOrderByOrderId(String orderid) throws YunPayException {
        // TODO Auto-generated method stub
        return otherOrderDAO.getOrderByOrderId(orderid);
    }

    @Override
    public OrderTO getOrderByAuthCode(String authCode) throws YunPayException {
        // TODO Auto-generated method stub
        return otherOrderDAO.getOrderByAuthCode(authCode);
    }

    @Override
    public OrderTO getOrderByTranNo(String tranno) throws YunPayException {
        // TODO Auto-generated method stub
        return otherOrderDAO.getOrderByTranNo(tranno);
    }
 
    public List<OrderTO> getOrders(OrderCriteriaTO selectCriteria)  throws YunPayException{
        return otherOrderDAO.getOrders(selectCriteria);
    }
}
