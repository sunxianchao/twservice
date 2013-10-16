/**
 * 
 */
package com.gamephone.pay.service;

import java.util.List;

import com.gamephone.common.to.OrderTO;
import com.gamephone.pay.criteria.OrderCriteriaTO;
import com.gamephone.pay.exception.YunPayException;

/**
 * @author xianchao.sun@downjoy.com 2012-9-20
 */
public interface OrderService {
    /**
     * 创建订单
     * @param order
     * @throws YunPayException
     */
    public void addOrder(OrderTO order) throws YunPayException;

    /**
     * 订单更新
     * @param order
     * @return
     * @throws YunPayException
     */
    public boolean updateOrderAndSendQueue(OrderTO order) throws YunPayException;

    public boolean updateOrder(OrderTO order) throws YunPayException;
    /**
     * 根据id号查询第三方平台订单
     * @param id
     * @return
     * @throws YunPayException
     */
    public OrderTO getOrderById(Integer id) throws YunPayException;

    /**
     * 根据orderid号查询第三方平台订单
     * @param id
     * @return
     * @throws YunPayException
     */
    public OrderTO getOrderByOrderId(String orderid) throws YunPayException;
    
    /**
     * 根据授权码获取订单
     * @param authCode
     * @return
     * @throws YunPayException
     */
    public OrderTO getOrderByAuthCode(String authCode) throws YunPayException;
    
    public OrderTO getOrderByTranNo(String tranno) throws YunPayException;
    
    public List<OrderTO> getOrders(OrderCriteriaTO selectCriteria)  throws YunPayException;
    
}
