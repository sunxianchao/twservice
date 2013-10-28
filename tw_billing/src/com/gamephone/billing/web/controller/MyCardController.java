package com.gamephone.billing.web.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gamephone.billing.exception.BillingException;
import com.gamephone.billing.model.Order;
import com.gamephone.billing.service.OrderService;
import com.gamephone.common.context.SystemProperties;
import com.gamephone.common.util.HTTPUtil;
import com.gamephone.common.util.JsonUtil;
import com.gamephone.common.util.MessageDigestUtil;
import com.gamephone.common.util.RequestUtil;

@Controller
public class MyCardController {

    private static final Logger logger=Logger.getLogger(MyCardController.class);
    
    private static final String mycardSecretKey="Pgdw5513";
    
    @Resource
    private OrderService orderService;
    
    /**
     * step2步骤由mycard发起
     * @param request
     * @param response
     */
    @RequestMapping(value="/common/mycard/iseligible", method=RequestMethod.POST)
    public void mycardIsEligible(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer=null;
        try {
            writer=response.getWriter();
            String data=RequestUtil.getString(request, "DATA");
            logger.info("revice iseligible  data:"+data);
            HashMap<String, String> dataMap=JsonUtil.Json2Object(data, new TypeReference<HashMap<String, String>>() {
            });
            String cpOrderId=dataMap.get("CP_TxID");
            StringBuilder basestring=new StringBuilder(mycardSecretKey);
            basestring.append(cpOrderId).append(dataMap.get("Account")).append(dataMap.get("Amount")).append(dataMap.get("Realm_ID")).append(dataMap.get("Character_ID"));
            logger.info(basestring.toString());
            String sign=MessageDigestUtil.getSHA1(basestring.toString());
            logger.info(sign);
            if(sign.equalsIgnoreCase(dataMap.get("SecurityKey"))){
                logger.info("sign success");
                Order order=orderService.getOrderByOrderId(cpOrderId);
                logger.info("cpOrderId"+cpOrderId);
                if(order != null){
                    writer.write("{\"ResultCode\":1}");
                }else{
                    throw new BillingException("invalid.order.error" + cpOrderId);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            writer.write("{\"ResultCode\":0}");
        }finally{
            if(writer != null){
                writer.flush();
                writer.close();
            }
        }
    }
    
    /**
     * step3和4都在这个方法中，step3由mycard发起此步骤已经完成扣款，step4是我方主动请求
     * @param request
     * @param response
     */
    @RequestMapping(value="/common/mycard/bridge", method=RequestMethod.POST)
    public void mycardBridge(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer=null;
        try {
            writer=response.getWriter();
            String data=RequestUtil.getString(request, "DATA");
            logger.info("bridge bridge data:"+data);
            HashMap<String, String> dataMap=JsonUtil.Json2Object(data, new TypeReference<HashMap<String, String>>() {
            });
            String cpOrderId=dataMap.get("CP_TxID");
            String MG_TxID=dataMap.get("MG_TxID");
            String Account=dataMap.get("Account");
            String Amount=dataMap.get("Amount");
            String Realm_ID=dataMap.get("Realm_ID");
            String Character_ID=dataMap.get("Character_ID");
            String Tx_Time=dataMap.get("Tx_Time");
            final String AUTH_CODE=dataMap.get("AUTH_CODE");
            String MyCardProjectNo=dataMap.get("MyCardProjectNo");
            String MyCardType=dataMap.get("MyCardType");
            StringBuilder basestring=new StringBuilder(mycardSecretKey);
            basestring.append(cpOrderId).append(MG_TxID).append(Account).append(Amount).append(Realm_ID)
                      .append(Character_ID).append(Tx_Time).append(AUTH_CODE).append(MyCardProjectNo).append(MyCardType);
            String sign=MessageDigestUtil.getSHA1(basestring.toString());
            if(sign.equalsIgnoreCase(dataMap.get("SecurityKey"))){
                Order order=orderService.getOrderByOrderId(cpOrderId);
                if(order != null){
                    order.setTradeNo(MG_TxID);
                    order.setAmount(Integer.valueOf(Amount) * 100);
                    order.setPaySuccess(1);
                    order.setResultCode("1");
                    order.setAuthCode(AUTH_CODE);
                    order.setProNo(MyCardProjectNo);
                    orderService.updateOrderAndSendQueue(order);
                    final Map<String, String> resultMap=new HashMap<String, String>();
                    resultMap.put("CP_TxID", order.getOrderId());
                    resultMap.put("AUTH_CODE", AUTH_CODE);
                    resultMap.put("MG_TxID", order.getTradeNo());
                    resultMap.put("ResultCode", order.getResultCode());
                    resultMap.put("Description", "交易完成");
                    writer.write(JsonUtil.Object2Json(resultMap));
                    //step4
                    resultMap.clear();
                    String securityKey=mycardSecretKey+AUTH_CODE+MG_TxID+Account;
                    securityKey=MessageDigestUtil.getSHA1(securityKey);
                    resultMap.put("AUTH_CODE", AUTH_CODE);
                    resultMap.put("MG_TxID", order.getTradeNo());
                    resultMap.put("Account", Account);
                    resultMap.put("SecurityKey", securityKey);
                    String params=JsonUtil.Object2Json(resultMap);
                    HTTPUtil.httpsPost(SystemProperties.getProperty("mycard.receive.url"), "DATA="+params, "utf-8");
                }else{
                    throw new BillingException("invalid.order.error" + cpOrderId);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            writer.write("{\"ResultCode\":0}");
        }finally{
            if(writer != null){
                writer.flush();
                writer.close();
            }
        }
    }
}
