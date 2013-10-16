package com.gamephone.admin.common.test;

import java.util.Random;

import com.gamephone.common.util.Base64;

public class KeyTest {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String merchantkey=getPass(8);
        System.out.println("merchantkey:" + merchantkey);
        String tmp=Base64.encode(merchantkey.getBytes());
        System.out.println("desKey:" + tmp);
        System.out.println("orgKey:"+ new String(Base64.decode(tmp)).toString());
        
    }

    public static String getPass(int passLenth) {
        String buffer="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
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
