package com.gamephone.admin.common.criteria;

import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.PayChannelTO;


public class PayChannelCriteriaTO extends PayChannelTO {

    private SearchPagerModel<PayChannelTO> pageModel;
    
    public SearchPagerModel<PayChannelTO> getPageModel() {
        return pageModel;
    }

    public void setPageModel(SearchPagerModel<PayChannelTO> pageModel) {
        this.pageModel=pageModel;
    }
    
}
