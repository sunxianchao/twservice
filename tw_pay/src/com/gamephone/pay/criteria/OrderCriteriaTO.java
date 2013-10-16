package com.gamephone.pay.criteria;

import java.util.Date;

import com.gamephone.common.to.OrderTO;


public class OrderCriteriaTO extends OrderTO {

    private Date startDate;
    
    private Date endDate;

    
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
