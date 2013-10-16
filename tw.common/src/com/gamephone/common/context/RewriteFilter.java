package com.gamephone.common.context;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.gamephone.common.util.FilterUtil;

public class RewriteFilter implements Filter {

    private static final Logger logger=Logger.getLogger(RewriteFilter.class);

    private String[] staticResource=new String[]{"/images/**", "/css/**", "/js/**", "/html/**", "/htm/**"};
    
    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // pass the request along the filter chain
        final HttpServletRequest req=(HttpServletRequest)request;
        
        final String url=FilterUtil.getRequestedUrl(req);

        logger.debug("url =============================" + url);

        if((url.length() > 1 && !url.contains(".")) || FilterUtil.checkRequestUrl(url, staticResource)) {
            req.getRequestDispatcher(url).forward(req, response);
            return;
        }

        req.getRequestDispatcher("/404.jsp").forward(req, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }
}
