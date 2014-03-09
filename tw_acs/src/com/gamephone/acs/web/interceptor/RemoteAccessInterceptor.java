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
        String requestIp=RequestUtil.getUserIpAddr(request);
        logger.info("remote ip:"+requestIp+"\turl:"+url);
        if(requestIp.indexOf("10.100.10.59")<0){
            logger.info("forbiden access");
            return false;
        }
        return true;
    }
}
