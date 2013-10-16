/**
 * 
 */
package com.gamephone.sender.common.dao;

import com.gamephone.common.to.OrderTO;

/**
 * @author xianchao.sun@downjoy.com
 * 2012-9-20
 */
public interface OrderDAO {

 
	/**
	 * 根据id号查询第三方平台订单
	 * @param id
	 * @return
	 * @throws YunPayException
	 */
	public OrderTO getOrderById(int id) throws Exception;
	
	/**
	 * 通知cp成功后修改通知状态和时间
	 * @param logId
	 * @param processStatusType
	 * @throws Exception
	 */
	public void updateNoticeStatus(String logId, int type) throws Exception;
 
}
