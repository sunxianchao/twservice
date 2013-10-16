/**
 * $Id: JSONView.java,v 1.2 2012/04/19 09:58:13 xianchao.sun Exp $
 */
package com.gamephone.admin.common.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.gamephone.admin.common.Constants;
import com.gamephone.admin.common.util.DwzJsonUtil;

/**
 * @author qinchao.zhang@downjoy.com
 */
public class JSONView extends AbstractView implements Constants {

    /**
     * 该View对应的输出类型
     */
    public String getContentType() {
        return "application/json; charset=UTF-8";
    }

    /**
     * 输出JSON数据
     * @param response
     * @param message JSON字符串
     */
    private void writeData(HttpServletResponse response, String message) {
        response.setContentType(getContentType());
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

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        Object res=model.get(JSON_ROOT);
        String jsonStr=DwzJsonUtil.getJSON(res);
        writeData(response, jsonStr);
        
    }


}
