/**
 * 
 */
package com.gamephone.pay;

/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-9-13
 */
public class PayConfig {

    public static final String PAY_SUCCESS_RESULT_MSG="交易完成";
    
    public static final String PAY_FAIL_RESULT_MSG="交易失败";
    
    /**************** 以下是支付宝商户合作账号信息 ********************/
    // 合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
    public static final String ALIPAY_PARTNER="2088801519572134";

    // 商户收款的支付宝账号
    public static final String ALIPAY_SELLER="miko.wang@yunyoyo.cn";

    // 商户（RSA）私钥
    public static final String ALIPAY_RSA_PRIVATE=
        "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALUWzpH1vE9IOJv0SiQZzL3ZVSfraXBBVrAOPpEf6YioAlGFO4zn26G2KDDxNWwB5RRwsbp8QmdFogQX0qHRBc/h4zgJt8lfykiUZLUzkuJhynizUh8EsSwdaBfg66WO3d8ewTqJrfDkMN+6GoFrecGboGELbLXckjTZXKgRzr9RAgMBAAECgYBx5FAzGHxA2MlRvIjMv7U3SfL20mZUBaQBwlOzboCwRAqBPuw/KKvHvwr0DSBuN5reCiqdgP06el71eA4vW4jtL1dIPJLGyBEMGHE8+nlc8gqLf0r+ztiNWFWqrqlnG+SNMiNufS3A6LjCGkZ0F6dQv+r/CzDj6NRqaFVME3OwgQJBAO/luEbNcIjuz/ppl650iSwg7dDWR0nGDIiXKWI2bssiQ6PdnHOrPf4vCclkRb0fJaFSen5a0vBPVNUVtqwOIQkCQQDBPo3vfaXyxSLCBTfBrCirSXzpRoDHYaaM9ZT4JHbwk9aqWUjxhr3WzhTf+e3uwIPPTT+p4Fd0ARG/r55admYJAkEAqwo4NpLm91p9gmaitMl1u7bGmC0OqrS/3usXoGR63e3SGBU7JOUk0AOtkJ8jcdGxPLHXkBslZhzJuRtAIK/6MQJARQfGhh25UR7QysQ4ZavNH1ryDxbLW+3rJAF6RKYqAsic719Tqku6tugJFvVd5GLK8xuiOVnVg6/6GiJvC9utmQJAIjpULCvknxIJD/XVAtBc4AUmFakEz9AzNsQXG4OEYfNxMyUto7NcAylP3DF5U3qm6O8HoA5FKZoUT3H7VEqkUg==";

    // 支付宝（RSA）公钥 用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取。
    public static final String ALIPAY_RSA_ALIPAY_PUBLIC=
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLZUhfSzJj7BkZW/FQpG7LEMHnvasl6KvZY+CjBXZJeiGhj9oCKX4k8QvfNn7bvJ4Li64A8DAOj4DnoibyEMjd5GtaFHB4tVM/teuyDhsuwC87fhATq1hZUgVIYMx8vZ8mg2mnF8TdAjMpMO9XLIw8vfyhN3741Gx+URBsIQhbDwIDAQAB";
         
    // 商户（MD5）KEY
    public static final String ALIPAY_KEY="y2a237to5bmlj6mgm1nefqzumk124z5t";

    // 签名类型
    public static final String ALIPAY_SIGN_TYPE="0001";

    // 支付前置服务器地址
    public static final String ALIPAY_PAYMENT_CHANNELS_URL="https://mapi.alipay.com/gateway.do";

    // 支付宝wap创建交易请求URL
    public static final String ALIPAY_REQ_URL="http://wappaygw.alipay.com/service/rest.htm";

    // 接收支付宝发送的通知的url
    public static final String ALIPAY_NOTIFY_RECEIVER_URL="http://pay.yunyoyo.cn/common/alipay/notify.html";

    // 支付成功跳转链接
    public static final String ALIPAY_CALLBACK_URL="http://pay.yunyoyo.cn/common/alipay/callback.html";

    // 未完成支付，用户点击链接返回商户url
    public static final String ALIPAY_FAILED_PAY_URL="http://pay.yunyoyo.cn/common/alipay/failed.html";

    /**************** 以下是神州付的支付的账号信息 ************************/
    // 神州行充值卡服务器直连接口入口
    public static final String SZF_PAY_URL="http://pay3.shenzhoufu.com/interface/version3/serverconnszx/entry-noxml.aspx";

    // 服务器返回地址
    public static final String SZF_CALLBACK_URL="http://pay.yunyoyo.cn/common/szf/callback.html";

    public static final String SZF_VERSION="3"; // 接口版本号

    public static final String SZF_MERID="125804"; // 商户ID

    public static final String SZF_MERUSERNAME="molly@yunyoyo.cn"; // 商户用户 名

    public static final String SZF_MERUSERMAIL="molly@yunyoyo.cn"; // 商户用户 Email

    public static final String SZF_PRIVATEFIELD="XZCA78ASDF"; // 商户私有数据,可以为空

    public static final String SZF_VERIFYTYPE="1";// MD5 校验

    public static final String SZF_DESKEY="AVRKgMfVzfc=";

    public static final String SZF_PRIVATEKEY="20120829";

    /*************** 以下是财付通的支付配置信息 ***********************/
    public static final String TENPAY_BARGAINOR_ID="1214649601";  

    public static final String TENPAY_KEY="1e6ad2309065edfcd35583c5f03e6450";

    public static final String TENPAY_CALLBACK_URL="http://pay.yunyoyo.cn/common/tenpay/callback.html";

    public static final String TENPAY_NOTIFY_URL="http://pay.yunyoyo.cn/common/tenpay/notify.html";
    
    /****************一下是台湾jcard（金流）相关配置*******************************/
    public static final String JCARD_PAY_URL="http://www.gamecard.com.tw/Payment/Choice.asp";
    
    public static final String JCARD_SERVICE_CODE="JID00097";
    
    public static final String JCARD_CALLBACK_URL="http://pay.yunyoyo.cn/common/jcard/notify.html";
    
    public static final String JCARD_PAY_KEY="UURJdHhVU29QM2M0YXhKWA==";//base64.decode
    

}
