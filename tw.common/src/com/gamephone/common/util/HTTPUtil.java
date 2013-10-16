package com.gamephone.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;

/**
 * @author xianchao.sun@downjoy.com
 */
public class HTTPUtil {

    private static final Logger logger=Logger.getLogger(HTTPUtil.class);

    public static String httpPost(String urlStr, String params, String charSet) {
        HttpURLConnection httpConn=null;
        try {
            byte[] data=params.getBytes(charSet);
            URL url=new URL(urlStr);
            httpConn=(HttpURLConnection)url.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("ContentType", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Content-Length", String.valueOf(data.length));
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//jdk1.4换成这个,连接超时
            // System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //jdk1.4换成这个,读操作超时
            httpConn.setConnectTimeout(30000);// jdk 1.5换成这个,连接超时
            httpConn.setReadTimeout(30000);// jdk 1.5换成这个,读操作超时
            httpConn.connect();
            OutputStream os1=httpConn.getOutputStream();
            os1.write(data);
            os1.flush();
            os1.close();
            return getResponseResult(httpConn, urlStr, charSet);
        } catch(Exception ex) {
            if(ex instanceof java.net.ConnectException || ex instanceof java.net.SocketTimeoutException) {
                NetUtil.clearDNSCache();
            }
            ex.printStackTrace();
            logger.error(urlStr, ex);
        } finally {
            if(null != httpConn) {
                httpConn.disconnect();
            }
        }
        return null;
    }
    
    public static String httpsPost(String urlStr, String params, String charSet) {
        HttpsURLConnection httpsConn=null;
        try {
            byte[] data=params.getBytes(charSet);
            URL url=new URL(urlStr);
            HttpsHandler.trustAllHttpsCertificates();
            HostnameVerifier hv=new HostnameVerifier() {

                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
            httpsConn=(HttpsURLConnection)url.openConnection();
            httpsConn.setRequestMethod("POST");
            httpsConn.setRequestProperty("ContentType", "application/x-www-form-urlencoded");
            httpsConn.setRequestProperty("Content-Length", String.valueOf(data.length));
            httpsConn.setDoInput(true);
            httpsConn.setDoOutput(true);
            httpsConn.setConnectTimeout(30000);// jdk 1.5换成这个,连接超时
            httpsConn.setReadTimeout(30000);// jdk 1.5换成这个,读操作超时
            httpsConn.connect();
            OutputStream os1=httpsConn.getOutputStream();
            os1.write(data);
            os1.flush();
            os1.close();
            return getResponseResult(httpsConn, urlStr, charSet);
        } catch(Exception ex) {
            if(ex instanceof java.net.ConnectException || ex instanceof java.net.SocketTimeoutException) {
                NetUtil.clearDNSCache();
            }
            ex.printStackTrace();
            logger.error(urlStr, ex);
        } finally {
            if(null != httpsConn) {
                httpsConn.disconnect();
            }
        }
        return null;
    }

    /**
     * post过去的参数分成不同的域发送过去
     * @param urlStr
     * @param params
     * @param charSet
     * @return
     */
    public static String httpPost(String urlStr, Map<String, String> params, String charSet) {
        HttpURLConnection httpConn=null;
        try {
            String paramstring=ParameterUtil.mapToUrl(params);
            URL url=new URL(urlStr);
            httpConn=(HttpURLConnection)url.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("ContentType", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Content-Length", String.valueOf(paramstring.length()));
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//jdk1.4换成这个,连接超时
            // System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //jdk1.4换成这个,读操作超时
            httpConn.setConnectTimeout(30000);// jdk 1.5换成这个,连接超时
            httpConn.setReadTimeout(30000);// jdk 1.5换成这个,读操作超时
            httpConn.connect();
            OutputStream os1=httpConn.getOutputStream();
            Iterator<String> it=params.keySet().iterator();
            int size=params.keySet().size();
            int i=1;
            while(it.hasNext()){
                String andStr="&";
                if(i==size){
                    andStr="";
                }
                String key=it.next();
                String val=params.get(key);
                byte[] data=(key+"="+URLEncoder.encode(val, "utf-8")+ andStr).getBytes();
                os1.write(data);
            }
            os1.flush();
            os1.close();
            return getResponseResult(httpConn, urlStr, charSet);
        } catch(Exception ex) {
            if(ex instanceof java.net.ConnectException || ex instanceof java.net.SocketTimeoutException) {
                NetUtil.clearDNSCache();
            }
            ex.printStackTrace();
            logger.error(urlStr, ex);
        } finally {
            if(null != httpConn) {
                httpConn.disconnect();
            }
        }
        return null;
    }
    
    public static String httpGet(String urlStr, String params, String charSet) {
        HttpURLConnection httpConn=null;
        try {
            if(null != params && params.length() > 0) {
                if(urlStr.indexOf("?") == -1) {
                    urlStr+="?" + params;
                } else {
                    urlStr+="&" + params;
                }
            }
            URL url=new URL(urlStr);
            httpConn=(HttpURLConnection)url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("ContentType", "application/x-www-form-urlencoded");
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//jdk1.4换成这个,连接超时
            // System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //jdk1.4换成这个,读操作超时
            httpConn.setConnectTimeout(30000);// jdk 1.5换成这个,连接超时
            httpConn.setReadTimeout(30000);// jdk 1.5换成这个,读操作超时
            httpConn.connect();
            return getResponseResult(httpConn, urlStr, charSet);
        } catch(Exception ex) {
            if(ex instanceof java.net.ConnectException || ex instanceof java.net.SocketTimeoutException) {
                NetUtil.clearDNSCache();
            }
            ex.printStackTrace();
            logger.error(urlStr, ex);
        } finally {
            if(null != httpConn) {
                httpConn.disconnect();
            }
        }
        return null;
    }
    
    public static String httpsGet(String urlStr, String params, String charSet) {
        HttpsURLConnection httpsConn=null;
        try {
            if(null != params && params.length() > 0) {
                if(urlStr.indexOf("?") == -1) {
                    urlStr+="?" + params;
                } else {
                    urlStr+="&" + params;
                }
            }
            byte[] data=params.getBytes(charSet);
            URL url=new URL(urlStr);
            HttpsHandler.trustAllHttpsCertificates();
            HostnameVerifier hv=new HostnameVerifier() {

                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
            httpsConn=(HttpsURLConnection)url.openConnection();
            httpsConn.setRequestMethod("GET");
            httpsConn.setRequestProperty("ContentType", "application/x-www-form-urlencoded");
            httpsConn.setRequestProperty("Content-Length", String.valueOf(data.length));
            httpsConn.setDoInput(true);
            httpsConn.setDoOutput(true);
            httpsConn.setConnectTimeout(30000);// jdk 1.5换成这个,连接超时
            httpsConn.setReadTimeout(30000);// jdk 1.5换成这个,读操作超时
            httpsConn.connect();
            OutputStream os1=httpsConn.getOutputStream();
            os1.write(data);
            os1.flush();
            os1.close();
            return getResponseResult(httpsConn, urlStr, charSet);
        } catch(Exception ex) {
            if(ex instanceof java.net.ConnectException || ex instanceof java.net.SocketTimeoutException) {
                NetUtil.clearDNSCache();
            }
            ex.printStackTrace();
            logger.error(urlStr, ex);
        } finally {
            if(null != httpsConn) {
                httpsConn.disconnect();
            }
        }
        return null;
    }

    private static String getResponseResult(HttpURLConnection httpConn, String urlStr, String charSet) throws IOException {
        String res=null;
        // 获得响应状态
        int responseCode=httpConn.getResponseCode();
        if(HttpURLConnection.HTTP_OK == responseCode) {
            byte[] buffer=new byte[1024];
            int len=-1;
            InputStream is=httpConn.getInputStream();
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            while((len=is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            res=bos.toString(charSet);
            is.close();
            bos.close();
            logger.error(urlStr + " Response Code:" + responseCode + " content:" + res);
        } else {
            logger.error(urlStr + " Response Code:" + responseCode);
        }
        return res;
    }

}
