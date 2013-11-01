package com.gamephone.acs.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gamephone.common.util.RequestUtil;


public class RemoteAccessInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger=Logger.getLogger(RemoteAccessInterceptor.class);
    
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url=request.getRequestURL().toString();
        logger.info(url);
        String requestIp=RequestUtil.getUserIpAddr(request);
        if(requestIp.indexOf("127.0.0.1")<0){
            return false;
        }
        return true;
    }
}
