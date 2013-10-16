package com.gamephone.pay.dao.ibatis;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.gamephone.common.ErrorCode;
import com.gamephone.common.to.OrderTO;
import com.gamephone.pay.criteria.OrderCriteriaTO;
import com.gamephone.pay.dao.OrderDAO;
import com.gamephone.pay.exception.YunPayException;

/**
 * @author xianchao.sun@downjoy.com 2012-9-20
 */
public class OrderDAOImpl extends SqlMapClientDaoSupport implements OrderDAO {

    @Override
    public void addOrder(OrderTO order) throws YunPayException {
        try {
            Integer id=(Integer)this.getSqlMapClient().insert("ORDER.addOrder", order);
            if(null == id) {
                throw new Exception("插入数据出错");
            }
            order.setId(id);
        } catch(Exception e) {
            throw new YunPayException(ErrorCode.DB_ERROR, e);
        }

    }

    @Override
    public boolean updateOrder(OrderTO order) throws YunPayException {
        try {
            return this.getSqlMapClient().update("ORDER.updateOrder", order) == 1;
        } catch(SQLException e) {
            throw new YunPayException(ErrorCode.DB_ERROR, e);
        }
    }

    @Override
    public OrderTO getOrderById(Integer id) throws YunPayException {
        try {
            return (OrderTO)this.getSqlMapClient().queryForObject("ORDER.getOrderById", id);
        } catch(SQLException e) {
            throw new YunPayException(ErrorCode.DB_ERROR, e);
        }
    }

    @Override
    public OrderTO getOrderByOrderId(String orderid) throws YunPayException {
        try {
            return (OrderTO)this.getSqlMapClient().queryForObject("ORDER.getOrderByOrderId", orderid);
        } catch(SQLException e) {
            throw new YunPayException(ErrorCode.DB_ERROR, e);
        }
        
    }
    
    @Override
    public OrderTO getOrderByTranNo(String tradeNO)  throws YunPayException{
        try {
            Map<String, String> param=new HashMap<String, String>();
            param.put("tradeNO", tradeNO);
            return (OrderTO)this.getSqlMapClient().queryForObject("ORDER.getOrderByTranNo", param);
        } catch(SQLException e) {
            throw new YunPayException(ErrorCode.DB_ERROR, e);
        }
    }

    @Override
    public OrderTO getOrderByAuthCode(String authCode) throws YunPayException {
        try {
            Map<String, String> param=new HashMap<String, String>();
            param.put("authCode", authCode);
            return (OrderTO)this.getSqlMapClient().queryForObject("ORDER.getOrderByAuthCode", param);
        } catch(SQLException e) {
            throw new YunPayException(ErrorCode.DB_ERROR, e);
        }
    }
    
    public List<OrderTO> getOrders(OrderCriteriaTO selectCriteria)  throws YunPayException{
        try {
            return (List<OrderTO>)this.getSqlMapClient().queryForList("ORDER.getOrders", selectCriteria);
        } catch(Exception e) {
            throw new YunPayException(ErrorCode.DB_ERROR, e);
        }
    }

}
