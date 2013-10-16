/**
 * $Id: ResponseUtil.java,v 1.1 2012/02/23 10:00:38 qinchao.zhang Exp $
 */
package com.gamephone.common.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {

    /**
     * 输出数据
     * @param response
     * @param message 字符串
     */
    public static void writeData(HttpServletResponse response, String message) {
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out=null;
        try {
            out=response.getWriter();
            out.print(message);
            out.flush();
        } catch(IOException e) {
        } finally {
            if(out != null) {
                out.close();
                out=null;
            }
        }
    }
}
