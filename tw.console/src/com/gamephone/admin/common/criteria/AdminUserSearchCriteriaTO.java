package com.gamephone.admin.common.criteria;

import com.gamephone.admin.common.to.AdminUserTO;
import com.gamephone.common.criteria.SearchPagerModel;

public class AdminUserSearchCriteriaTO extends AdminUserTO {

    private SearchPagerModel<AdminUserTO> pageModel;

    public SearchPagerModel<AdminUserTO> getPageModel() {
        return pageModel;
    }

    public void setPageModel(SearchPagerModel<AdminUserTO> pageModel) {
        this.pageModel=pageModel;
    }

}
