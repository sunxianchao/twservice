/**
 * $Id: YunPayExceptionHandler.java,v 1.1 2012/09/25 06:07:36 xianchao.sun Exp $
 */
package com.gamephone.pay.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.gamephone.common.ErrorCode;
import com.gamephone.common.to.ErrorCodeTO;
import com.gamephone.common.to.ResponseTO;
import com.gamephone.common.util.JsonUtil;
import com.gamephone.common.util.ResponseUtil;


/**
 * @author xianchao.sun@downjoy.com
 */
public class YunPayExceptionHandler implements HandlerExceptionResolver {

    private static final Logger logger=Logger.getLogger(YunPayExceptionHandler.class);

    private static final String exceptionName=YunPayException.class.getSimpleName() + ":";

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        handlerError(request, response, ex);
        return new ModelAndView("error");
    }

    public static void handlerError(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        String msg=null;
        msg=ex.getMessage();
        logger.error("handlerError:", ex);
        if(null != msg) {
            int ind=msg.indexOf(exceptionName);
            if(ind > 0) {
                msg=msg.substring(ind + exceptionName.length());
            }
            logger.error(msg, ex);
        }
        if(response.isCommitted()) {
            return;
        }
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        ResponseTO<Object> responseTO=new ResponseTO<Object>();
        responseTO.setSuccess(false);
        ErrorCodeTO errorCode=null;
        if(ex instanceof YunPayException) {
            YunPayException tmpEx=(YunPayException)ex;
            errorCode=tmpEx.getErrorCode();
        } else if(ex.getCause() instanceof YunPayException) {
            YunPayException tmpEx=(YunPayException)ex.getCause();
            errorCode=tmpEx.getErrorCode();
        } else {
            errorCode=ErrorCode.UNKOWN_ERROR;
        }
        if(errorCode == null && msg != null){
            errorCode= new ErrorCodeTO("0", msg);
        }
        responseTO.setErrorCode(errorCode);
        try {
            String jsonStr=JsonUtil.Object2Json(responseTO);
            response.setContentType("application/json; charset=UTF-8");
            ResponseUtil.writeData(response, jsonStr);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
