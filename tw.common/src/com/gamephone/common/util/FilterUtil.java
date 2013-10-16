package com.gamephone.common.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class FilterUtil {

    private static PathMatcher pathMatcher=new AntPathMatcher();

    /**
     * 鍒ゆ柇鎵�姹傜殑URL 鏄惁鍖归厤鎵�寚瀹歶rl pattern 鍒楄〃銆�
     * @param requestUrl 璇锋眰鐨刄RL
     * @param urlResources URL Pattern 鍒楄〃
     * @return
     */
    public static boolean checkRequestUrl(final String requestUrl, final List<String> urlResources) {
        boolean isExisted=false;

        if(urlResources != null) {
            for(String url: urlResources) {
                isExisted=pathMatcher.match(url.toLowerCase(), requestUrl.toLowerCase());
                if(isExisted) {
                    break;
                }
            }
        }
        return isExisted;
    }

    
    public static boolean checkRequestUrl(final String requestUrl, final String[] urlResources) {
        boolean isExisted=false;

        if(urlResources != null) {
            for(String url: urlResources) {
                isExisted=pathMatcher.match(url.toLowerCase(), requestUrl.toLowerCase());
                if(isExisted) {
                    break;
                }
            }
        }
        return isExisted;
    }
    /**
     * @param urlPatterns
     * @return
     */
    public static List<String> spliteUrlPatterns(String urlPatterns) {
        List<String> urls=null;
        if(StringUtils.isNotBlank(urlPatterns)) {
            String[] tmp=StringUtils.split(urlPatterns, "\n");
            urls=new ArrayList<String>();

            for(String url: tmp) {
                if(StringUtils.isNotBlank(url)) {
                    urls.add(url.trim());
                }
            }
        }

        return urls;
    }

    /**
     * 鑾峰緱璇锋眰璺緞鐨刄RI
     * @param req ServletRequest
     * @return
     */
    public static String getRequestedUrl(ServletRequest req){
        final HttpServletRequest request = (HttpServletRequest) req;
        // 鑾峰緱URL
        String url = request.getRequestURI();
        String context = request.getContextPath();
        if(url.startsWith(context+"/")) {
            url = url.replaceFirst(context, "");
        }
        
        return url;
    }
}
