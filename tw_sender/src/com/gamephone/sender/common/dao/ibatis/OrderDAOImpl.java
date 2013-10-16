/**
 * 
 */
package com.gamephone.sender.common.dao.ibatis;


import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.gamephone.common.to.OrderTO;
import com.gamephone.sender.common.dao.OrderDAO;

/**
 * @author xianchao.sun@downjoy.com
 * 2012-9-20
 */
public class OrderDAOImpl extends SqlMapClientDaoSupport implements OrderDAO {

    private static final Logger logger=Logger.getLogger(OrderDAOImpl.class);
	@Override
	public OrderTO getOrderById(int id) throws Exception {
		return (OrderTO)this.getSqlMapClient().queryForObject("ORDER.getOrderById", id);
	}
 
	public void updateNoticeStatus(String orderId, int type) throws Exception {
        Map<String, String> map=new HashMap<String, String>();
        map.put("orderId", orderId);
        map.put("noticeStatus", String.valueOf(type));
        int c=this.getSqlMapClientTemplate().update("ORDER.updateNoticeStatus", map);
        logger.info("update other order notify orderid:"+orderId+", status:"+map.get("noticeStatus")+"count="+c);
    }
}
