/**
 * $Id: PayMentUtil.java,v 1.1 2012/09/25 06:07:36 xianchao.sun Exp $
 */
package com.gamephone.pay.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class PayMentUtil {

    private static final ReentrantLock orderIdLock=new ReentrantLock();

    private static int serialNum=0;

    private static final char[] CHARS=new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
        'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
        'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static void writeResultCode(HttpServletResponse response, String resultCode, String msg) throws IOException {
        OutputStreamWriter write=new OutputStreamWriter(response.getOutputStream(), "UTF-8");
        BufferedWriter writer=new BufferedWriter(write);
        String str="result=" + resultCode;
        if(null != msg) {
            str+="&msg=" + msg;
        }
        writer.write(str);
        writer.flush();
        writer.close();
    }

    /**
     * 生成订单号 // {渠道id}{游戏序号}{服务器序号}{时间戳+随机码+自增码}
     * @param merchantId
     * @param gameId
     * @return
     */
    public static String genOrderId(String spno, Integer gameId, Integer serverId) {

        String gid=gameId.toString() == null ? "" : gameId.toString();
        while(gid.length() < 4) {
            gid="0" + gid;
        }
        String sid=serverId.toString() == null ? "" : serverId.toString();
        while(sid.length() < 4) {
            sid="0" + sid;
        }
        StringBuilder sb=new StringBuilder(spno);
        sb.append(gid);
        sb.append(sid);

        try {
            orderIdLock.lock();
            int m=serialNum / CHARS.length;
            int n=serialNum % CHARS.length;

            sb.append(CHARS[m]);
            sb.append(CHARS[n]);
            if(serialNum >= (62 * 62 - 1)) {
                serialNum=0;
            } else {
                serialNum++;
            }
            sb.append(Long.toString(System.currentTimeMillis(), 36));
        } finally {
            orderIdLock.unlock();
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String a=Long.toString(System.currentTimeMillis(), 36);
        System.out.println(a);
    }

    
    // 得到xml字符串节点内容
    public static String getXmlValue(String xml, String name) {
        if(StringUtils.isBlank(xml) || StringUtils.isBlank(name)) {
            return "";
        }
        int start=xml.indexOf("<" + name + ">");
        start+=(name.length() + 2);// 去掉本字符串和"<"、">"的长度
        int end=xml.indexOf("</" + name + ">");
        if(end > start && end <= (xml.length() - name.length() - 2)) {
            return xml.substring(start, end);
        } else {
            return "";
        }
    }
}
