package com.gamephone.acs.exception;

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

public class AcsExceptionHandler implements HandlerExceptionResolver{
    

    private static final Logger logger=Logger.getLogger(AcsExceptionHandler.class);

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
        if(ex instanceof AcsException) {
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
