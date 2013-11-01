package com.gamephone.billing.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public Result<String> updateOrder(HttpServletRequest request, HttpServletResponse response) throws BillingException,
        IOException {
        Integer orderid=RequestUtil.getInteger(request, "orderid");
        String tradeNO=RequestUtil.getString(request, "tradeno");
        Integer status=RequestUtil.getInteger(request, "status");
        String uid=RequestUtil.getString(request, "uid");
        String productId=RequestUtil.getString(request, "productid");
        String signedData=RequestUtil.getString(request, "signeddata");
        String signature=RequestUtil.getString(request, "signature");
        String amountString=SystemProperties.getProperty("google.billing.productid."+productId);
        if(StringUtils.isBlank(amountString)){
            throw new BillingException("未识别的产品id："+productId);
        }
        logger.info("googlebilling_productId:"+productId+"\torderi d:"+orderid+"\tstatus:"+status);
        int amount=Integer.valueOf(amountString);
        Order order=orderService.getOrderById(orderid);
        Result<String> res=new Result<String>();
        if(order != null) {
            String encodedPublicKey=SystemProperties.getProperty("google.public.key.gameid"+order.getGameId());
            logger.info("publickey:"+encodedPublicKey);
            boolean isVerify=GooglePlaySecurity.verify(encodedPublicKey, signedData, signature);
            if(!isVerify){
                logger.info("google verify error signedData:"+signedData+"\tsignature:"+signature);
                throw new BillingException("google verify error");
            }
            logger.info("request userId:"+uid+"\torderid:"+order.getId()+"\tuserid:"+order.getUserId()+"\tradeNO:"+tradeNO);
            if(uid ==null || !uid.equals(order.getUserId())){
                throw new BillingException("非法用户："+uid);
            }
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
            HTTPUtil.httpPost(SystemProperties.getProperty("mycard.receive.url")+SystemProperties.getProperty("user.payment.info.url"), "amount="+order.getAmount()+"&userId="+order.getUserId(), "utf-8");
        } else {
            throw new BillingException("未知订单");
        }
        return res;
    }
    
    public static void main(String[] args) {
        String encodedPublicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzgHFT8LcwdPPOKxhtRzwfGUf7MIyDIMHmPgK+imIzHDr4lefXDuefrTEQnjmn0tkMZiZ6jPeri7ShvaFA7LseIDHKI9ghNZA3Bh1+GA0fWvTBhHkPJUqhwmPBm861t0NlBQDyl0IwT7weiCb0tYx6gZl952t64j9yDBjScA4A6oyp3YusVKuAoq3K712+cBTeaZlj6LuddXIEXsivB2hXk2XU6R0PCJ8u0/eSX8dOe3HcKYRUbDY6lI9MWDPnw+IugtXJdnYozRXxfMh/exq854U9GLuAKZLXfIMdP91U75m1NAAhgTifF/Z0+kAxk3ZjX8yEdKn429SwKp9pIckPQIDAQAB";
        String signdata="{\"orderId\":\"12999763169054705758.1379061891092812\",\"packageName\":\"com.tuomi.happysanguoandroid_tw\",\"productId\":\"com.phonegame.dw3.gpay_f.01\",\"purchaseTime\":1369725198000,\"purchaseState\":0,\"developerPayload\":\"com.phonegame.dw3\",\"purchaseToken\":\"qqwpirxvdyqjsfbhakziatuk.AO-J1Owwx6sJ7GvGHJ8x203n7_31LWo4MbdAdjhD3mYpcRcd-bXxaO1BB9L08aQIqmcJjCGK9ju6lHkmsIhLvL_xlCCgkhtMjjTPvTFxk0iND54xDAr5bCNJK-LnZ_ra1mG57Oum3G-5MfD9A2lWuZCLhW998GKz5Q\"}";
        String sign="hcGpNx2R/Fs2HeN7/GHccMsUxP7PHKEvLe08YFoAHV4MQ4Fjp865SkyTPA3qWBtYIBirAXut0GJQyVmngzIXqgd17uC/SgHO6pkpPH7FZ2BJAFSCBfQS8OljbNayumFPMt81rb8Sr+gXitubE2ENoLr+qqOw3HRyJV/hGIWDCe9pSl4ZZdSBVOlwIaRHjKydcjH1bZC73jFkJYAnoVGBmIVZJ5KK2f9joZL1FOVM7CQsCtPnDRBrEq7QJbK3PHb23Xr0/bm74rJLnCjjctNKQXglKZlzWWFzEuU53Eg2B/Tzgs2OTW7PJPcNhc0xFnf1jD+/Hyg3TSdtuoxOldBVlA==";
        boolean status=GooglePlaySecurity.verify(encodedPublicKey, signdata, sign);
        System.out.println(status);
    }
}
