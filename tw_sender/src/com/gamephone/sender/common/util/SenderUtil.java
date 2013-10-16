/**
 * $Id: SenderUtil.java,v 1.4 2012/05/09 06:30:09 jiayu.qiu Exp $
 */
package com.gamephone.sender.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.gamephone.common.util.Base64;
import com.gamephone.common.util.MessageDigestUtil;


/**
 * @author jiayu.qiu@downjoy.com
 */
public class SenderUtil {

    private static final SimpleDateFormat DATE_TIME_FORMAT=new SimpleDateFormat("yyyyMMddHHmmss");

    public static boolean isSuccessed(String returnMsg) {
        return null != returnMsg && returnMsg.toLowerCase().indexOf("success") != -1;
    }

    public static String builderReturnParams(String tradeNo, Integer cpId, Integer gameSeqNum, Integer serverSeqNum, String amount,
        String userId, String extInfo, String remark, String secretKey) throws Exception {
        LinkedHashMap<String, String> parmsMap=new LinkedHashMap<String, String>();
        parmsMap.put("trade_no", tradeNo);
        parmsMap.put("cpid", String.valueOf(cpId));
        parmsMap.put("game_seq_num", String.valueOf(gameSeqNum));
        parmsMap.put("server_seq_num", String.valueOf(serverSeqNum));
        parmsMap.put("amount", amount);
        parmsMap.put("user_id", String.valueOf(userId));
        parmsMap.put("ext_info", extInfo);
        parmsMap.put("timestamp", DATE_TIME_FORMAT.format(new Date()));

        String verstr=mapToUrl(parmsMap) + "&notifyKey=" + new String(Base64.decode(secretKey));
        verstr=MessageDigestUtil.getMD5(verstr);
        if(null != extInfo) {
            parmsMap.put("ext_info", URLEncoder.encode(extInfo, "UTF-8"));
        }
        parmsMap.put("verstring", verstr);
        if(null!=remark){
            parmsMap.put("remark", URLEncoder.encode(remark, "UTF-8"));
        }
        return mapToUrl(parmsMap);
    }

    public static String mapToUrl(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder sb=new StringBuilder();
        boolean isFirst=true;
        for(String key: params.keySet()) {
            String value=params.get(key);
            if(isFirst) {
                sb.append(key + "=" + value);
                isFirst=false;
            } else {
                if(value != null) {
                    sb.append("&" + key + "=" + value);
                } else {
                    sb.append("&" + key + "=");
                }
            }
        }
        return sb.toString();
    }
    
    
    public static void main(String[] args) {
        String s="trade_no=998000800110108Vuhe7kw3n9&cpid=231&game_seq_num=2&server_seq_num=3&amount=100&user_id=2019149&ext_info=46909&timestamp=20130313092011&SecretKey=pitfTBab";
        String d=MessageDigestUtil.getMD5(s);
        System.out.println(d);
    }

}
