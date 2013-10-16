/**
 * $Id: SecurityFilter.java,v 1.1 2012/09/25 06:07:37 xianchao.sun Exp $
 */
package com.gamephone.pay.web.filter;

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
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.gamephone.common.Constants;
import com.gamephone.common.ErrorCode;
import com.gamephone.common.to.GameTO;
import com.gamephone.common.util.Base64;
import com.gamephone.common.util.FilterUtil;
import com.gamephone.common.util.MessageDigestUtil;
import com.gamephone.common.util.ParameterUtil;
import com.gamephone.common.util.RequestUtil;
import com.gamephone.pay.cache.GameCache;
import com.gamephone.pay.exception.YunPayException;
import com.gamephone.pay.exception.YunPayExceptionHandler;
 
/**
 * 安全验证
 * <p>
 * 要求所有的请求都厂商编号（cpid）和签名（sign）两个参数
 * </p>
 * @author xianchao.sun@downjoy.com
 */
public class SecurityFilter implements Filter {

    private static final Logger logger=Logger.getLogger(SecurityFilter.class);

    private static String ignoreUrl="/common/*";
    
    private List<String> ignoreUrlList;

    private static PathMatcher pathMatcher = new AntPathMatcher();


    public void init(FilterConfig filterConfig) throws ServletException {
        String param=filterConfig.getInitParameter("ignoreUrlList");
        if(StringUtils.isNotBlank(param)) {
            ignoreUrl=param;
        }
        ignoreUrlList=FilterUtil.spliteUrlPatterns(ignoreUrl);
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException,
        ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        String servletPath=request.getServletPath();
        try {
            checkService(request, servletPath);
            chain.doFilter(request, response);
        } catch(YunPayException e) {
            YunPayExceptionHandler.handlerError(request, response, e);
        }
    }

    /**
     * 验证用户是否已经登录
     * @param request
     * @throws SmartNgCoreException
     */
    private void checkService(HttpServletRequest request, String servletPath) throws YunPayException {
         final boolean isexist=FilterUtil.checkRequestUrl(servletPath, ignoreUrlList);
         if(!isexist){
             String sign=RequestUtil.getString(request, Constants.SIGN_PARAM_NAME);
             if(null == sign) {
                 throw new YunPayException(ErrorCode.NO_SIGN_ERROR);
             }
             Integer cpId=RequestUtil.getInteger(request, Constants.CP_ID);
             if(null == cpId) {
                 throw new YunPayException(ErrorCode.NO_CPID_ERROR);
             }
             Integer seqNum=RequestUtil.getInteger(request, Constants.SEQ_NUM);
             if(null == seqNum) {
                 throw new YunPayException(ErrorCode.GAME_NOT_EXIST);
             }
             GameTO game=GameCache.getGameTO(cpId, seqNum);
             Map<String, String[]> params=new HashMap<String, String[]>(request.getParameterMap());
             String signData=ParameterUtil.getSignData(params);
             if(null == game) {
                 logger.error("get game is null");
                 throw new YunPayException(ErrorCode.GAME_NOT_EXIST);
             }
             String sign2=null;
             try {
                 sign2=MessageDigestUtil.getMD5(signData + new String(Base64.decode(game.getSecretKey())));
             } catch(Exception e) {
                 logger.error(e.getMessage(), e);
             }
             if(!sign.equals(sign2)) {
                 throw new YunPayException(ErrorCode.SIGN_ERROR);
             }
         }
    }
    
 


    @Override
    public void destroy() {
        
    }
}
