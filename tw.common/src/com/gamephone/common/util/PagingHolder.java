package com.gamephone.common.util;

import java.io.Serializable;
import java.util.List;

public class PagingHolder<T> implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID=4266893764468760933L;

    // 当前的page 数
    private int page=0;

    // 每页需要显示的条数
    private int size=10;

    // 结果种数
    private int resultNum;

    // 结果集
    private List<T> result;

    // 排序列
    private String orderColumn;

    // 排序方向
    private String order;

    // 查询的开始
    private int startNum;

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn=orderColumn;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order=order;
    }

    public PagingHolder(int page, int size) {
        super();
        this.page=page;
        this.size=size;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result=result;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public int getResultNum() {
        return resultNum;
    }

    public void setResultNum(int resultNum) {
        this.resultNum=resultNum;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum=startNum;
    }
}
