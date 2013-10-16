package com.gamephone.admin.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamephone.admin.common.criteria.OrderCriteriaTO;
import com.gamephone.admin.common.dao.OrderDAO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.admin.common.service.OrderService;
import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.OrderTO;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDAO orderDAO;
    
    public SearchPagerModel<OrderTO> getOrderList(OrderCriteriaTO order) throws AdminException {
        return orderDAO.getOrderList(order);
    }

    public Integer getTotalAmount(OrderCriteriaTO order) throws AdminException {
        return orderDAO.getTotalAmount(order);
    }
    

    public OrderTO getOrderById(Integer id) throws AdminException{
        return orderDAO.getOrderById(id);
    }
    
    
}
