package com.gamephone.admin.common.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.gamephone.admin.common.criteria.PayChannelCriteriaTO;
import com.gamephone.admin.common.dao.PayChannelDAO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.PayChannelTO;


public class PayChannelDAOImpl extends SqlMapClientDaoSupport implements PayChannelDAO {

    public SearchPagerModel<PayChannelTO> getPayChannels(PayChannelCriteriaTO channel) throws AdminException {
        SearchPagerModel<PayChannelTO> pager=channel.getPageModel();
        Integer count=(Integer)getSqlMapClientTemplate().queryForObject("PAY.getPayChannelCNT", channel);
        if(null != count && count.intValue() > 0) {
            List<PayChannelTO> result=getSqlMapClientTemplate().queryForList("PAY.getPayChannels", channel);
            pager.setResultList(result);
            pager.setTotal(count);
        }
        return pager;
    }

    public void updatePayChannelStatus(PayChannelTO channel) throws AdminException {
        getSqlMapClientTemplate().update("PAY.updatePayChannelStatus", channel);
    }

    public void addChildPayChannel(PayChannelTO channel) throws AdminException {
        getSqlMapClientTemplate().insert("PAY.addChildPayChannel", channel);
    }

    public void addParentPayChannel(PayChannelTO channel) throws AdminException {
        getSqlMapClientTemplate().insert("PAY.addParentPayChannel", channel);
    }
}
