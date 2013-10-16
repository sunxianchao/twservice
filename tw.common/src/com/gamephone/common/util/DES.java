/**
 * $Id: DES.java,v 1.3 2012/02/24 08:23:38 jiayu.qiu Exp $
 */
package com.gamephone.common.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * DES加密的，文件中共有两个方法,加密、解密
 * @author jiayu.qiu@downjoy.com
 */
public class DES {

    /**
     * 已知密钥的情况下加密
     */
    public static String encode(String str, String key) throws Exception {
        byte[] rawKey=Base64.decode(key);
        IvParameterSpec sr=new IvParameterSpec(rawKey);
        DESKeySpec dks=new DESKeySpec(rawKey);
        SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("DES");
        SecretKey secretKey=keyFactory.generateSecret(dks);

        javax.crypto.Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);

        byte data[]=str.getBytes("UTF8");
        byte encryptedData[]=cipher.doFinal(data);
        return Base64.encode(encryptedData).trim();
    }

    /**
     * 已知密钥的情况下解密
     */
    public static String decode(String str, String key) throws Exception {
        byte[] rawKey=Base64.decode(key);
        IvParameterSpec sr=new IvParameterSpec(rawKey);
        DESKeySpec dks=new DESKeySpec(rawKey);
        SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("DES");
        SecretKey secretKey=keyFactory.generateSecret(dks);
        Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
        byte encryptedData[]=Base64.decode(str);
        byte decryptedData[]=cipher.doFinal(encryptedData);
        return new String(decryptedData, "UTF8").trim();
    }

}
