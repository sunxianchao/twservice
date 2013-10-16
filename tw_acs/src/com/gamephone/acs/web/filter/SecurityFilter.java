/**
 * $Id: SecurityFilter.java,v 1.1 2012/09/25 06:07:37 xianchao.sun Exp $
 */
package com.gamephone.acs.web.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gamephone.acs.exception.AcsException;
import com.gamephone.acs.exception.AcsExceptionHandler;
import com.gamephone.acs.cache.GameCache;
import com.gamephone.common.Constants;
import com.gamephone.common.cache.OnlineUserCache;
import com.gamephone.common.context.GlobeContext;
import com.gamephone.common.context.SystemProperties;
import com.gamephone.common.to.GameTO;
import com.gamephone.common.to.OnlineUser;
import com.gamephone.common.util.Base64;
import com.gamephone.common.util.FilterUtil;
import com.gamephone.common.util.MessageDigestUtil;
import com.gamephone.common.util.ParameterUtil;
import com.gamephone.common.util.RequestUtil;

/**
 * 安全验证
 * @author xianchao.sun@downjoy.com
 */
public class SecurityFilter implements Filter {

    private static final Logger logger=Logger.getLogger(SecurityFilter.class);

    private static String ignoreUrl="/common/*";

    private List<String> ignoreUrlList;

    private OnlineUserCache onlineUserCache;
    
    private GameCache gameCache;
    
    public void init(FilterConfig filterConfig) throws ServletException {
        String param=filterConfig.getInitParameter("ignoreUrlList");
        if(StringUtils.isNotBlank(param)) {
            ignoreUrl=param;
        }
        ignoreUrlList=FilterUtil.spliteUrlPatterns(ignoreUrl);
        onlineUserCache=(OnlineUserCache)GlobeContext.getApplicationContext().getBean("onlineUserCache");
        gameCache=(GameCache)GlobeContext.getApplicationContext().getBean("gameCache");
        logger.info("SecurityFilter init success");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException,
        ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        try {
            checkOnlineUser(request);
            checkRequest(request);
        } catch(AcsException e) {
            e.printStackTrace();
            AcsExceptionHandler.handlerError(request, response, e);
        }
        chain.doFilter(request, response);
    }

    private void checkOnlineUser(HttpServletRequest httpServletRequest) throws AcsException{
        if(!ignoreUrlList.contains(httpServletRequest.getServletPath())){
            String token = httpServletRequest.getHeader("token");
            try {
                OnlineUser onlineUser=onlineUserCache.getOnlineUser(token);
                if(onlineUser == null){
                    throw new AcsException(SystemProperties.getProperty("login.session.timeout"));
                }
                httpServletRequest.setAttribute(Constants.ONLINE_USER, onlineUser);
            } catch(Exception e) {
                e.printStackTrace();
                throw new AcsException(SystemProperties.getProperty("login.session.timeout"));
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    private void checkRequest(HttpServletRequest request) throws AcsException{
        String sign=RequestUtil.getString(request, Constants.SIGN_PARAM_NAME);
        if(StringUtils.isEmpty(sign)){
            throw new AcsException(SystemProperties.getProperty("lose.sign.data"));
        }
        Integer gameId=RequestUtil.getInteger(request, "gameId");
        GameTO game=gameCache.getGameById(gameId);
        if(game == null){
            throw new AcsException(SystemProperties.getProperty("game.is.not.exist"));
        }
        Map<String, String[]> params=new HashMap<String, String[]>(request.getParameterMap());
        String baseString=ParameterUtil.getSignData(params);
        String serverSign=MessageDigestUtil.getMD5(baseString + new String(Base64.decode(game.getSecretKey())));
        if(!sign.equals(serverSign)){
            throw new AcsException(SystemProperties.getProperty("invalid.request.sign.verifycation.error"));
        }
    }
    
    @Override
    public void destroy() {
        onlineUserCache=null;
        gameCache=null;
    }
}
