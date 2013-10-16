package com.gamephone.admin.common.service;

import com.gamephone.admin.common.criteria.OrderCriteriaTO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.OrderTO;


public interface OrderService {

    SearchPagerModel<OrderTO> getOrderList(OrderCriteriaTO order) throws AdminException;
    
    Integer getTotalAmount(OrderCriteriaTO order) throws AdminException; 
    
    OrderTO getOrderById(Integer id) throws AdminException; 
}
