/**
 * 
 */
package com.gamephone.admin.common.criteria;

import java.util.Date;

import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.OrderTO;


/**
 * @author xianchao.sun@downjoy.com
 *
 */
public class OrderCriteriaTO extends OrderTO {
    
    private SearchPagerModel<OrderTO> pageModel;
    
    private Date startDate;

    private Date endDate;

    private Long promptId;

    private Integer minAmount;

    private Integer maxAmount;

    private Integer amountOption;
    
    private String[] pids;

    private int[][] amountArray=new int[][]{{0, 50}, {50, 100}, {100, 500}, {500, 1000}, {1000, Integer.MAX_VALUE}};
    public SearchPagerModel<OrderTO> getPageModel() {
        return pageModel;
    }

    public void setPageModel(SearchPagerModel<OrderTO> pageModel) {
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

    public Integer getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Integer minAmount) {
        this.minAmount=minAmount;
    }

    public Integer getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Integer maxAmount) {
        this.maxAmount=maxAmount;
    }

    public Integer getAmountOption() {
        return amountOption;
    }

    public void setAmountOption(Integer amountOption) {
        this.amountOption=amountOption;
        if(null != amountOption) {
            this.minAmount=amountArray[amountOption][0] * 100;
            this.maxAmount=amountArray[amountOption][1] * 100;
        }
    }

    public Long getPromptId() {
        return promptId;
    }

    public void setPromptId(Long promptId) {
        this.promptId=promptId;
    }

    
    public String[] getPids() {
        return pids;
    }

    
    public void setPids(String[] pids) {
        this.pids=pids;
    }
    
}
