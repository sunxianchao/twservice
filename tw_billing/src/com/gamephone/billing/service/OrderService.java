/**
 * 
 */
package com.gamephone.billing.service;

import com.gamephone.billing.exception.BillingException;
import com.gamephone.billing.model.Order;

/**
 * @author xianchao.sun@downjoy.com 2012-9-20
 */
public interface OrderService {
    /**
     * 创建订单
     * @param order
     * @throws BillingException
     */
    public void createOrder(Order order) throws BillingException;

    /**
     * 订单更新
     * @param order
     * @return
     * @throws BillingException
     */
    public boolean updateOrderAndSendQueue(Order order) throws BillingException;

    public boolean updateOrder(Order order) throws BillingException;
    /**
     * 根据id号查询第三方平台订单
     * @param id
     * @return
     * @throws BillingException
     */
    public Order getOrderById(Integer id) throws BillingException;

    /**
     * 根据orderid号查询第三方平台订单
     * @param id
     * @return
     * @throws BillingException
     */
    public Order getOrderByOrderId(String orderid) throws BillingException;
    
    /**
     * 根据授权码获取订单
     * @param authCode
     * @return
     * @throws BillingException
     */
    public Order getOrderByAuthCode(String authCode) throws BillingException;
    
    public Order getOrderByTranNo(String tranno, Integer channelId) throws BillingException;
    
}
