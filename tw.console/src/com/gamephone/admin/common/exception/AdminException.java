/**
 * $Id: AdminException.java,v 1.1 2012/03/23 11:46:32 xianchao.sun Exp $
 */
package com.gamephone.admin.common.exception;

/**
 * @author xianchao.sun@downjoy.com
 */
public class AdminException extends Exception {

    private static final long serialVersionUID=1L;

    public AdminException() {

    }

    public AdminException(String message) {
        super(message);
    }

    public AdminException(Throwable cause) {
        super(cause);
    }

    public AdminException(String message, Throwable cause) {
        super(message, cause);
    }

}
