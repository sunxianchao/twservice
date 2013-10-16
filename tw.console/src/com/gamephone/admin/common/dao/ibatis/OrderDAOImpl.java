package com.gamephone.admin.common.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.gamephone.admin.common.criteria.OrderCriteriaTO;
import com.gamephone.admin.common.dao.OrderDAO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.OrderTO;


public class OrderDAOImpl extends SqlMapClientDaoSupport implements OrderDAO{

    public SearchPagerModel<OrderTO> getOrderList(OrderCriteriaTO order) throws AdminException {
        SearchPagerModel<OrderTO> pager=order.getPageModel();
        Integer count=(Integer)getSqlMapClientTemplate().queryForObject("ORDER.getOrderCNT", order);
        if(null != count && count.intValue() > 0) {
            List<OrderTO> result=getSqlMapClientTemplate().queryForList("ORDER.getOrderList", order);
            pager.setResultList(result);
            pager.setTotal(count);
        }
        return pager;
    }

    public Integer getTotalAmount(OrderCriteriaTO order) throws AdminException {
        return (Integer)getSqlMapClientTemplate().queryForObject("ORDER.getTotalAmount", order);
    }
    
    public OrderTO getOrderById(Integer id) throws AdminException{
        return (OrderTO)getSqlMapClientTemplate().queryForObject("ORDER.getOrderById", id);
    }

}
