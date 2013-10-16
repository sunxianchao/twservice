package com.gamephone.billing.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.gamephone.common.context.SystemProperties;
import com.gamephone.common.to.Result;
import com.gamephone.common.util.JsonUtil;

public class BillingExceptionHandler implements HandlerExceptionResolver{
    

    private static final Logger logger=Logger.getLogger(BillingExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception ex) {
        handlerError(request, response, ex);
        return null;
    }
    
    public static void handlerError(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        logger.error("handlerException:", ex);
        if(response.isCommitted()) {
            return;
        }
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        Result<Object> result=new Result<Object>();
        result.setSuccess(false);
        if(ex instanceof BillingException) {
            if(StringUtils.isNotEmpty(ex.getMessage())){
                result.setErrorMsg(ex.getMessage());
            }else{
                result.setErrorMsg(SystemProperties.getProperty("unknow.exception"));
            }
        }
        PrintWriter out=null;
        try {
            out=response.getWriter();
            String json=JsonUtil.Object2Json(result);
            out.print(json);
//            out.print("<html><body style='background: url(/images/bdlogo.gif) no-repeat center center;'></body></html>");
            out.flush();
        }catch(IOException e) {
        } finally {
            if(out != null) {
                out.close();
                out=null;
            }
        }
        
    }
}
