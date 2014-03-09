package com.gamephone.admin.common.criteria;

import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.User;


public class UserCriteriaTO extends User{

    private SearchPagerModel<User> pageModel;

    public SearchPagerModel<User> getPageModel() {
        return pageModel;
    }

    public void setPageModel(SearchPagerModel<User> pageModel) {
        this.pageModel=pageModel;
    }
}
