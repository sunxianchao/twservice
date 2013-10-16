/**
 * $Id: ErrorCodeTO.java,v 1.5 2012/03/15 03:09:27 jiayu.qiu Exp $
 */
package com.gamephone.common.to;

/**
 * 错误代码
 * @author jiayu.qiu@downjoy.com
 */
public class ErrorCodeTO {

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误说明
     */
    private String msg;

    public ErrorCodeTO() {
    }

    public ErrorCodeTO(String code, String msg) {
        this.code=code;
        this.msg=msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code=code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg=msg;
    }

}
