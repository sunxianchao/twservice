package com.gamephone.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieHelper {

    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies=request.getCookies();
        if(cookies != null) {
            for(int i=0; i < cookies.length; i++) {
                Cookie c=cookies[i];
                if(c.getName().equalsIgnoreCase(cookieName)) {
                    return c;
                }
            }
        }
        return null;
    }

    public static Cookie getCookie4FirstVisit(HttpServletRequest request, String cookieName) {
        Cookie[] cookies=request.getCookies();
        if(cookies != null) {
            for(int i=0; i < cookies.length; i++) {
                Cookie c=cookies[i];
                if(c.getName().equalsIgnoreCase(cookieName)) {
                    return c;
                }
            }

            if(request.getAttribute(cookieName) != null) {
                return (Cookie)request.getAttribute(cookieName);
            }
        } else {
            if(request.getAttribute(cookieName) != null) {
                return (Cookie)request.getAttribute(cookieName);
            }
        }

        return null;
    }
}
