/**
 * $Id: AdminExceptionHandler.java,v 1.4 2012/04/16 09:43:20 jiayu.qiu Exp $
 */
package com.gamephone.admin.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.gamephone.admin.common.Constants;
import com.gamephone.admin.common.util.DwzJsonUtil;
import com.gamephone.common.util.RequestUtil;

/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-3-23
 */
public class AdminExceptionHandler implements HandlerExceptionResolver , Constants{

    private static Logger logger=Logger.getLogger(AdminExceptionHandler.class);

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String msg=null;
        msg=ex.getMessage();
        logger.error(msg, ex);        
        return handlerError(request, response, msg);
    }

    public static ModelAndView handlerError(HttpServletRequest request, HttpServletResponse response, String msg) {
        if(response.isCommitted()) {
            return null;
        }
        if(RequestUtil.isAjax(request)) {
            return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getErrorStatusMsg(msg));
        } else {
            request.setAttribute("errorMsg", msg);
            return new ModelAndView("/error.jsp");
        }
    }
}
