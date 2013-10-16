package com.gamephone.common.util;

import java.util.Random;


public class RadomUtil {

    public static String getPass(int passLenth) {
        String buffer="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb=new StringBuilder();
        Random r=new Random();
        int range=buffer.length();
        for(int i=0; i < passLenth; i++) {
            // 生成指定范围类的随机数0—字符串长度(包括0、不包括字符串长度)
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }
}
