package com.gamephone.billing.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gamephone.billing.exception.BillingException;
import com.gamephone.billing.model.Order;
import com.gamephone.billing.service.OrderService;
import com.gamephone.billing.util.GooglePlaySecurity;
import com.gamephone.common.context.SystemProperties;
import com.gamephone.common.to.Result;
import com.gamephone.common.util.HTTPUtil;
import com.gamephone.common.util.RequestUtil;


@Controller
public class GooglePlayController {

    private static final Logger logger=Logger.getLogger(GooglePlayController.class);

    @Autowired
    private OrderService orderService;
    
    @RequestMapping("/service/order/googlebillingupdate")
    public @ResponseBody Result<String> updateOrder(HttpServletRequest request, HttpServletResponse response) throws BillingException,
        IOException {
        String orderid=RequestUtil.getString(request, "orderid");
        String tradeNO=RequestUtil.getString(request, "tradeno");
        Integer status=RequestUtil.getInteger(request, "status");
//        String uid=RequestUtil.getString(request, "uid");
        String productId=RequestUtil.getString(request, "productid");
        String signedData=RequestUtil.getString(request, "signeddata");
        String signature=RequestUtil.getString(request, "signature");
        String amountString=SystemProperties.getProperty("google.billing.productid."+productId);
        if(StringUtils.isBlank(amountString)){
            throw new BillingException("未识别的产品id："+productId);
        }
        logger.info("googlebilling_productId:"+productId+"\torderid:"+orderid+"\tstatus:"+status);
        int amount=Integer.valueOf(amountString);
        Order order=orderService.getOrderByOrderId(orderid);
        Result<String> res=new Result<String>();
        if(order != null) {
            String encodedPublicKey=SystemProperties.getProperty("google.public.key.gameid"+order.getGameId());
            logger.info("publickey:"+encodedPublicKey);
            boolean isVerify=GooglePlaySecurity.verify(encodedPublicKey, signedData, signature);
            if(!isVerify){
                logger.info("new google verify error signedData:"+signedData+"\tsignature:"+signature);
                encodedPublicKey=SystemProperties.getProperty("google.public.key.gameid"+order.getGameId()+".old");
                isVerify=GooglePlaySecurity.verify(encodedPublicKey, signedData, signature);
                if(!isVerify){
                	logger.info("old google verify error signedData:"+signedData+"\tsignature:"+signature);
                		throw new BillingException("google verify error");
                }
            }
            logger.info("orderid:"+order.getId()+"\tuserid:"+order.getUserId()+"\tradeNO:"+tradeNO);
//            if(uid ==null || !uid.equals(order.getUserId())){
//                throw new BillingException("非法用户："+uid);
//            }
            order.setAmount(amount * 100);
            order.setTradeNo(tradeNO);
            String msg=Integer.valueOf(status) == 0 ? "完成交易" : "交易失敗";
            int payStatus=Integer.valueOf(status) == 0 ? 1 : 0;
            order.setPaySuccess(payStatus);
            order.setResultMsg(msg);
            order.setType(3);
            order.setProductId(productId);
            order.setResultCode(String.valueOf(payStatus));
            orderService.updateOrderAndSendQueue(order);
            res.setSuccess(true);
            res.setBusinessResult(order.getOrderId());
            String params="amount="+order.getAmount()+"&userId="+order.getUserId();
            System.out.println(params);
            HTTPUtil.httpPost(SystemProperties.getProperty("remote.acs.domain")+SystemProperties.getProperty("user.payment.info.url"), params, "utf-8");
        } else {
            throw new BillingException("未知订单");
        }
        return res;
    }
    
    public static void main(String[] args) { 
        String encodedPublicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj2Fw/czb5t1zR3A1T6iLw76sGKLTC+8FpeH0yGaejH3ctpOvUqIws7YHkss7gvr3XnY7TyiVA51V4sZtLTRTuwhja2VQ/zqjuUJRKJBjSahXqrj0txvoqd/nN7yWvTfzKwk8uFXebFC7wbzjd+B2t3sA/wa+jGzeQsttap61Q2NybE9sNWVpjF656W2IltzfCn8NDFOmZ5gJhq3M75UCuJ/uEXk9Cl84kJQ7T83abeFhoItbor9HeD9ZsQApi2r4zG/lOI0aANByHsUEqzCOYIJhg9YOEaUJbqve8r4uCJ9K+cQ0RYB0xYUgYKp1Z+3tMwso0ztasAxIgia6thhIAwIDAQAB";
        String signdata="{\"orderId\":\"12999763169054705758.1394840302107456\",\"packageName\":\"com.tuomi.happysanguoandroid\",\"productId\":\"com.phonegame.dw3.gpay_f.06\",\"purchaseTime\":1389901732225,\"purchaseState\":0,\"developerPayload\":\"com.phonegame\",\"purchaseToken\":\"lwvfubxndxzeyrmbkhbdxdqo.AO-J1Owa5eNNFbokR2B_3V9xIN10PcTtVsiVpcLVlNSjI-BqB_L8TLBCJDwSCRph_RZg0OBPAf7T26ZhdLcFV3Mi1PyfphZiN6hPvA2F67NiV9ybqNRsFeeSvwA1HaOOc2ytn_FIGBK-YJSqDGGVJ1FA83MwWj-2mg\"}";
        String sign="iN3RO1l9YGxZC5LeEXsz4fC61qiZTRbsCw1ezkW6q7QRxbjIh09FwhNahhFefBBLgr4cfGcASmGzsxCnHwd2155GIgqf4wY34FaALtEafFd93gNgRsvenIsnwIwlC6c6g6snppcn0YCQVpiERASHFtP4jAxTuqhjZMm7vsJMzlqwtA6MQ9QkdPp5hZH/eIS1L0QHSlCiFH/0ranyJhgIkE2VwU+FBMrRRqJEiEuWczyADnPonByF6iHLLm6RufIr607nZoRnKdJiS9PuAmms5m2AyG1QtFSPiY65TC4J+kUm/uhD3nndrlWnedYCHwQUl8hAB3HHZCRE7gp0zDD+QA==";
        boolean status=GooglePlaySecurity.verify(encodedPublicKey, signdata, sign);
        System.out.println(status);
    }
}
