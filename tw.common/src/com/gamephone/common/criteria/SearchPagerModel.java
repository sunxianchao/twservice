/**
 * $Id: SearchPagerModel.java,v 1.6 2012/03/30 09:51:03 jiayu.qiu Exp $
 */
package com.gamephone.common.criteria;

import java.util.List;

/**
 * 用于分页查询时计算 offset，minIndex， maxIndex 并用于记录查询结果：total，totalPage， resultList
 * @author jiayu.qiu@downjoy.com
 * @param <O>
 */

public class SearchPagerModel<O> {

    // 总数
    private int total;

    // 当前页码,默认为1
    private int currentPage=1;

    // 总页数
    private int totalPage;

    // 页面大小,默认为20
    private int pageSize=20;

    // 开始,默认为0
    private int offset=0;

    private int minIndex=0;

    private int maxIndex=0;

    // 查询出来的数据
    private List<O> resultList;
    
    public SearchPagerModel(){
        
    }

    public SearchPagerModel(int currentPage, int pageSize) {
        init(currentPage, pageSize);
    }

    /**
     * 统一初始化所有参数
     * @param currentPage 当前页码
     * @param pageSize 页面大小
     */
    public void init(int currentPage, int pageSize) {
        // 当传入的数不大于0时,把当前页面设为了1,页面大小设为20
        if(currentPage < 1) {
            this.currentPage=1;
        } else {
            this.currentPage=currentPage;
        }
        this.pageSize=pageSize;
        this.offset=(this.currentPage - 1) * this.pageSize;
        this.minIndex=this.offset;
        this.maxIndex=this.currentPage * this.pageSize;
    }

    public void setTotal(int total) {
        this.total=total;
        if(this.pageSize != 0){
            // 计算出总页数
            int a=this.total / this.pageSize; // 商
            int b=this.total % this.pageSize; // 余
            if(0 != b) {
                a++;
            }
            if(a == 0) {
                a++;
            }
            this.totalPage=a;
        }else{
            this.totalPage=1;
        }
        if(this.currentPage > this.totalPage) {
            init(this.totalPage, this.pageSize);
        }
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset=offset;
    }

    public List<O> getResultList() {
        return resultList;
    }

    public void setResultList(List<O> resultList) {
        this.resultList=resultList;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage=currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize=pageSize;
    }

    public int getTotal() {
        return total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage=totalPage;
    }

    public int getMinIndex() {
        return minIndex;
    }

    public void setMinIndex(int minIndex) {
        this.minIndex=minIndex;
    }

    public int getMaxIndex() {
        return maxIndex;
    }

    public void setMaxIndex(int maxIndex) {
        this.maxIndex=maxIndex;
    }

}
