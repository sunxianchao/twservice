package com.gamephone.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author xianchao.sun@downjoy.com
 */
public class MessageDigestUtil {

    public static String getMessageDigest(byte[] buffer, String key) {
        MessageDigest digest;
        try {
            digest=MessageDigest.getInstance(key);
            digest.reset();
            digest.update(buffer);
            return bytes2Hex(digest.digest());
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    public static String getMD5(byte[] buffer) {
        return getMessageDigest(buffer, "MD5");
    }
    
    public static String getMD5(String str) {
        try {
            return getMD5(str.getBytes("UTF-8"));
        } catch(UnsupportedEncodingException e) {
        }
        return null;
    }

    public static String getSHA1(byte[] buffer) {
        return getMessageDigest(buffer, "SHA-1");
    }

    public static String getSHA1(String str) {
        try {
            return getSHA1(str.getBytes("UTF-8"));
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String getSHA256(byte[] buffer) {
        return getMessageDigest(buffer, "SHA-256");
    }

    public static String getSHA256(String str) {
        try {
            return getSHA256(str.getBytes("UTF-8"));
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
