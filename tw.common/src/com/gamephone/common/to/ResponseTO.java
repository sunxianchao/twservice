package com.gamephone.common.to;

/**
 * 调用平台接口返回的结果
 */
public class ResponseTO<T> {

    /**
     * 是否调用成功 默认为false 所以在每次调用都必须设置这个值为true
     */
    private boolean isSuccess=false;

    /**
     * 调用的业务成功结果 如果调用失败 这个将是空值
     */
    private T businessResult;

    /**
     * 错误信息
     */
    private ErrorCodeTO errorCode;

    /**
     * 登录票据
     */
    private String ticket;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess=isSuccess;
    }

    public T getBusinessResult() {
        return businessResult;
    }

    public void setBusinessResult(T businessResult) {
        this.businessResult=businessResult;
    }

    public ErrorCodeTO getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCodeTO errorCode) {
        this.errorCode=errorCode;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket=ticket;
    }

}
