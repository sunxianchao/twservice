package com.gamephone.billing.dao;

import org.apache.ibatis.annotations.Param;

import com.gamephone.billing.exception.BillingException;
import com.gamephone.billing.model.Order;


public interface OrderMapper {


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
    public boolean updateOrder(Order order) throws BillingException;

    /**
     * 根据id号查询第三方平台订单
     * @param id
     * @return
     * @throws BillingException
     */
    public Order getOrderById(@Param("id") Integer id) throws BillingException;

    /**
     * 根据orderid号查询第三方平台订单
     * @param id
     * @return
     * @throws BillingException
     */
    public Order getOrderByOrderId(@Param("orderId") String orderId) throws BillingException;
    
    /**
     * 根据授权码获取订单
     * @param authCode
     * @return
     * @throws BillingException
     */
    public Order getOrderByAuthCode(@Param("authCode") String authCode) throws BillingException;
    
    
    public Order getOrderByTranNo(@Param("tradeNo") String tradeNo, @Param("channelId") Integer channelId) throws BillingException;
    
}
