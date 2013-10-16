package com.gamephone.admin.common.dao;

import com.gamephone.admin.common.criteria.OrderCriteriaTO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.OrderTO;


public interface OrderDAO {

    public SearchPagerModel<OrderTO> getOrderList(OrderCriteriaTO order) throws AdminException; 
    
    public Integer getTotalAmount(OrderCriteriaTO order) throws AdminException; 
    
    public OrderTO getOrderById(Integer id) throws AdminException; 
}
