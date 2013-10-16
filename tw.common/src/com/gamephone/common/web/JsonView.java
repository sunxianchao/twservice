package com.gamephone.common.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.gamephone.common.Constants;
import com.gamephone.common.cache.OnlineUserCache;
import com.gamephone.common.to.OnlineUser;
import com.gamephone.common.util.JsonUtil;


public class JsonView extends AbstractView{


    private static final String JSON_TYPE="application/json; charset=UTF-8";
    
    private OnlineUserCache onlineUserCache;
    
    public String getContentType() {
        return JSON_TYPE;
    }
    
    public void setOnlineUserCache(OnlineUserCache onlineUserCache) {
        this.onlineUserCache=onlineUserCache;
    }


    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out=null;
        try {
            out=response.getWriter();
            OnlineUser onlineUser=(OnlineUser)request.getAttribute("onlineUser");
            if(onlineUser != null){
                response.setHeader("token", onlineUser.getToken());
                onlineUserCache.writeOnlineUser(onlineUser);
            }
            Object result=map.get(Constants.JSON_ROOT);
            String json=JsonUtil.Object2Json(result);
            out.print(json);
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
