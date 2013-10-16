/**
 * $Id: YunPayException.java,v 1.1 2012/09/25 06:07:36 xianchao.sun Exp $
 */
package com.gamephone.pay.exception;

import com.gamephone.common.to.ErrorCodeTO;


public class YunPayException extends Exception {

    private static final long serialVersionUID=1L;

    private ErrorCodeTO errorCode;

    public YunPayException(String msg) {
        super(msg);
    }

    public YunPayException(ErrorCodeTO errorCode) {
        this.errorCode=errorCode;
    }

    public YunPayException(ErrorCodeTO errorCode, String message) {
        super(message);
        this.errorCode=errorCode;
    }

    public YunPayException(ErrorCodeTO errorCode, Throwable cause) {
        super(cause);
        this.errorCode=errorCode;
    }

    public ErrorCodeTO getErrorCode() {
        return this.errorCode;
    }

}
