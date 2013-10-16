/**
 * $Id: GameCriteriaTO.java,v 1.1 2012/03/31 09:21:17 lili.meng Exp $
 */
package com.gamephone.admin.common.criteria;

import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.GameTO;

/**
 * 
 * @author lili.meng@downjoy.com
 *
 */
public class GameCriteriaTO extends GameTO {

    private SearchPagerModel<GameTO> searchPagerModel;

    public SearchPagerModel<GameTO> getSearchPagerModel() {
        return searchPagerModel;
    }

    public void setSearchPagerModel(SearchPagerModel<GameTO> searchPagerModel) {
        this.searchPagerModel=searchPagerModel;
    }
}
