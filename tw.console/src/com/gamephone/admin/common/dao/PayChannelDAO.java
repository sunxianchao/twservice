/**
 * $Id: PayChannelDAO.java,v 1.1 2012/07/26 10:10:35 xianchao.sun Exp $
 */
package com.gamephone.admin.common.dao;

import java.util.List;

import com.gamephone.admin.common.criteria.PayChannelCriteriaTO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.PayChannelTO;


/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-7-24
 *
 */
public interface PayChannelDAO {

    /**
     * 获取全部支付方式列表
     * @return
     */
    public SearchPagerModel<PayChannelTO> getPayChannels(PayChannelCriteriaTO channel) throws AdminException;
    
    /**
     * 修改支付方式状态 （是否可用）
     * @param channel
     */
    public void updatePayChannelStatus(PayChannelTO channel)throws AdminException;
    
    public void addChildPayChannel(PayChannelTO channel)throws AdminException;
    
    public void addParentPayChannel(PayChannelTO channel)throws AdminException;
    
    
}
