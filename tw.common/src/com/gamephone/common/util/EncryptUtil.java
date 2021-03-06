package com.gamephone.common.util;

import org.apache.commons.codec.digest.DigestUtils;

public class EncryptUtil {

    /**
     * 利用MD5进行加密
     * @param str 待加密的字符串
     * @return 加密后的字符串
     */
    public static String encoderByMd5(String str) {
        // 确定计算方法
        if(str == null)
            str="";
        return DigestUtils.md5Hex(str).substring(0, 18);
    }

    public static String EncodeByRC4(String aInput, String aKey) {
        int[] iS=new int[256];
        byte[] iK=new byte[256];

        for(int i=0; i < 256; i++)
            iS[i]=i;

        int j=1;

        for(short i=0; i < 256; i++) {
            iK[i]=(byte)aKey.charAt((i % aKey.length()));
        }

        j=0;

        for(int i=0; i < 255; i++) {
            j=(j + iS[i] + iK[i]) % 256;
            int temp=iS[i];
            iS[i]=iS[j];
            iS[j]=temp;
        }

        int i=0;
        j=0;
        char[] iInputChar=aInput.toCharArray();
        char[] iOutputChar=new char[iInputChar.length];
        for(short x=0; x < iInputChar.length; x++) {
            i=(i + 1) % 256;
            j=(j + iS[i]) % 256;
            int temp=iS[i];
            iS[i]=iS[j];
            iS[j]=temp;
            int t=(iS[i] + (iS[j] % 256)) % 256;
            int iY=iS[t];
            char iCY=(char)iY;
            iOutputChar[x]=(char)(iInputChar[x] ^ iCY);
        }

        return new String(iOutputChar);

    }

    public static String EncodeCookieValue(String aInput, String aKey) {
        String str=EncodeByRC4(aInput, aKey);
        String base64Str="";
        try {
            base64Str=Base64.encode(ConvertUtil.getBytesFromObject(str));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return base64Str.replace("\r\n", "");
    }

    public static String DecodeCookieValue(String base64Str, String aKey) {
        String str="";
        try {
            str=EncodeByRC4(ConvertUtil.getObjectFromBytes(Base64.decode(base64Str)).toString(), aKey);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // String inputStr = "测试aqbcd_查重";
        String inputStr="jfhksjkfljdkljfkjshfukjrefbrh;ejkfndjsnfmcnjdsjknjkshbjfdklsnfm,bnmsmm,bfdmsnfsdnfds,mm,";
        String key="abcdefg";

        String ss=EncodeCookieValue(inputStr, key);
        System.out.println(Base64.encode(ConvertUtil.getBytesFromObject(inputStr)) + "|" + ss.replace("\r\n", ""));
        System.out.println(DecodeCookieValue(ss.replace("\r\n", ""), key));

        // String str = EncodeByRC4(inputStr,key);
        // String base64Str="";
        // base64Str = new BASE64Encoder().encode(ConvertUtil.getBytesFromObject(str));
        // System.out.println(base64Str);
        //
        // //打印加密后的字符串
        // System.out.println(str);
        //
        // //打印解密后的字符串
        // System.out.println(EncodeByRC4(str,key));
        // System.out.println(EncodeByRC4(ConvertUtil.getObjectFromBytes(new
        // BASE64Decoder().decodeBuffer(base64Str)).toString(),key));
    }

}
