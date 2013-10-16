/**
 * $Id: SecurityFilter.java,v 1.3 2012/03/28 02:58:14 xianchao.sun Exp $
 */
package com.gamephone.admin.common.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.gamephone.admin.common.to.AdminUserTO;
import com.gamephone.admin.common.util.DwzJsonUtil;
import com.gamephone.common.util.PathUtil;
import com.gamephone.common.util.RequestUtil;

/**
 * 登陆验证
 */
public class SecurityFilter implements Filter {

    private static String redirectUrl="/login.html";

    private static String dateStr;

    private static OutputStreamWriter fileWriter;

    private static SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");

    private static SimpleDateFormat dateTimeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final Logger logger=Logger.getLogger(SecurityFilter.class);

    private static final List<String> ignoreUrlList=new ArrayList<String>();

    public void init(FilterConfig filterConfig) throws ServletException {
        String temp=filterConfig.getInitParameter("ignoreUrl");
        if(null != temp && !"".equals(temp)) {
            String[] ts=temp.split(";");
            for(String t: ts) {
                ignoreUrlList.add(t);
            }
        }
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException,
        ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        HttpSession session=((HttpServletRequest)request).getSession(false);
        String servletPath=request.getServletPath();

        if(null != request.getPathInfo()) {
            servletPath+=request.getPathInfo();
        }

        if(ignoreUrlList.contains(servletPath)) {
            chain.doFilter(request, response);
            return;
        }
        AdminUserTO adminUser=Funcs.getSessionUser(session);
        if(null == adminUser) {
            if(RequestUtil.isAjax(request)) {
                PrintWriter out=response.getWriter();
                response.setContentType("application/json; charset=UTF-8");
                out.println(DwzJsonUtil.getSessionTimeOutMsg());
            } else {
                response.sendRedirect(request.getContextPath() + redirectUrl);
            }
            return;
        }
        writeAccessLog(adminUser, servletPath, request);
        chain.doFilter(request, response);
    }

    private void writeAccessLog(AdminUserTO adminUser, String servletPath, HttpServletRequest request) {
        Date now=new Date();

        if(dateStr == null || dateFormat.format(now) != dateStr) {
            dateStr=dateFormat.format(now);
            try {
                if(fileWriter != null) {
                    fileWriter.close();
                }

                File file=new File(PathUtil.getInstance().getAccessLogPath(dateStr));
                File dir=file.getParentFile();
                if(!dir.exists()) {
                    dir.mkdirs();
                }
                fileWriter=new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8");
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        String subIp=request.getHeader("Proxy-Client-IP");
        if(subIp == null || subIp.length() == 0) {
            subIp=request.getHeader("X-Forwarded-For");
        }
        StringBuilder buf=new StringBuilder();
        buf.append(dateTimeFormat.format(now)).append("|");
        buf.append(adminUser.getId()).append("|").append(adminUser.getEmail()).append("|");
        buf.append(request.getRemoteAddr()).append("|").append(RequestUtil.getSubIpAddr(request)).append("|");
        buf.append(servletPath).append("|").append(RequestUtil.getAllRequestParameter(request)).append("|");
        buf.append("\r\n");
        try {
            fileWriter.write(buf.toString());
            fileWriter.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

}
