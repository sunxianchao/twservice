package com.gamephone.admin.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamephone.admin.common.criteria.PayChannelCriteriaTO;
import com.gamephone.admin.common.dao.PayChannelDAO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.admin.common.service.PayChannelService;
import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.PayChannelTO;

@Service
public class PayChannelServiceImpl implements PayChannelService {

    @Autowired
    private PayChannelDAO payChannelDAO;

    public SearchPagerModel<PayChannelTO> getPayChannels(PayChannelCriteriaTO channel) throws AdminException {
        return payChannelDAO.getPayChannels(channel);
    }

    public void updatePayChannelStatus(PayChannelTO channel) throws AdminException {
        payChannelDAO.updatePayChannelStatus(channel);
    }

    public void addChildPayChannel(PayChannelTO channel) throws AdminException {
        payChannelDAO.addChildPayChannel(channel);
    }

    public void addParentPayChannel(PayChannelTO channel) throws AdminException {
        payChannelDAO.addParentPayChannel(channel);
    }
}
