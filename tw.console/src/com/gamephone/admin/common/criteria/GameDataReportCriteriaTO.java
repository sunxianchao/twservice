/**
 * $Id: GameDataReportCriteriaTO.java,v 1.1 2012/04/19 09:56:25 xianchao.sun Exp $
 */
package com.gamephone.admin.common.criteria;

import java.util.Date;

import com.gamephone.admin.common.to.GameDataReportTO;
import com.gamephone.common.criteria.SearchPagerModel;


/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-4-18
 *
 */
public class GameDataReportCriteriaTO extends GameDataReportTO {

    private SearchPagerModel<GameDataReportTO> pageModel;

    private Date startDate;
    
    private Date endDate;
    
    public SearchPagerModel<GameDataReportTO> getPageModel() {
        return pageModel;
    }

    
    public void setPageModel(SearchPagerModel<GameDataReportTO> pageModel) {
        this.pageModel=pageModel;
    }


    
    public Date getStartDate() {
        return startDate;
    }


    
    public void setStartDate(Date startDate) {
        this.startDate=startDate;
    }


    
    public Date getEndDate() {
        return endDate;
    }


    
    public void setEndDate(Date endDate) {
        this.endDate=endDate;
    }
    
    
}
