package com.gamephone.pay.web.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gamephone.common.context.SystemProperties;
import com.gamephone.common.to.GameTO;
import com.gamephone.common.to.OrderTO;
import com.gamephone.common.to.PayChannelTO;
import com.gamephone.common.util.HTTPUtil;
import com.gamephone.common.util.JsonUtil;
import com.gamephone.common.util.MessageDigestUtil;
import com.gamephone.common.util.ParameterUtil;
import com.gamephone.common.util.RequestUtil;
import com.gamephone.common.Constants;
import com.gamephone.common.ErrorCode;
import com.gamephone.pay.cache.GameCache;
import com.gamephone.pay.cache.PayChannelCache;
import com.gamephone.pay.criteria.OrderCriteriaTO;
import com.gamephone.pay.exception.YunPayException;
import com.gamephone.pay.service.OrderService;
import com.gamephone.pay.util.DateUtil;
import com.gamephone.pay.util.PayMentUtil;

@Controller
public class MyCardController {

    private static final Logger logger=Logger.getLogger(MyCardController.class);

    @Resource
    private OrderService orderService;

    @RequestMapping("/service/mycard/index")
    public void mycardIndex(HttpServletRequest request, HttpServletResponse response) throws YunPayException {
        ModelAndView mv=new ModelAndView("error");
        Integer cpId=RequestUtil.getInteger(request, Constants.CP_ID);
        Integer seqNum=RequestUtil.getInteger(request, Constants.SEQ_NUM);
        Integer serverId=RequestUtil.getInteger(request, "serverId");
        String uId=RequestUtil.getString(request, "uId");
        String sign=RequestUtil.getString(request, "sign");
        String ext_info=RequestUtil.getString(request, "ext_info");
        mv.addObject("sign", sign);
        mv.addObject("cpId", cpId);
        mv.addObject("seqNum", seqNum);
        mv.addObject("serverId", serverId);
        mv.addObject("uId", uId);
        mv.addObject("ext_info", ext_info);
        try {
            request.getRequestDispatcher("/mycard/billing/show").forward(request, response);
            return;
        } catch(Exception e) {
            e.printStackTrace();
            mv.addObject("errorMsg", "验证失败");
        }
    }

    @RequestMapping("/mycard/ingame/author")
    public ModelAndView ingameGetAuthCode(HttpServletRequest request, HttpServletResponse response) throws YunPayException {
        ModelAndView mv=new ModelAndView("card");
        String ext_info=RequestUtil.getString(request, "ext_info");
        OrderTO order=createOrder(request);
        if(order != null) {
            HashMap<String, String> params=new HashMap<String, String>();
            String faceId=SystemProperties.getProperty("mycard.ingame.facid");
            params.put("facId", faceId);
            params.put("facTradeSeq", order.getOrderId());
            String key1=SystemProperties.getProperty("mycard.ingame.key1.gameid" + order.getGameId());
            String key2=SystemProperties.getProperty("mycard.ingame.key2.gameid" + order.getGameId());
            StringBuilder hash=new StringBuilder();
            hash.append(key1).append(faceId).append(order.getOrderId()).append(key2);
            params.put("hash", MessageDigestUtil.getSHA256(hash.toString()));
            try {
                String paramsString=ParameterUtil.mapToUrl(params);
                String res=HTTPUtil.httpGet(SystemProperties.getProperty("mycard.ingame.auth.url"), paramsString, "utf-8");
                HashMap<String, Object> resMap=JsonUtil.Json2Object(res, new TypeReference<HashMap<String, Object>>() {
                });
                String authCode=resMap.get("AuthCode").toString();
                String returnMsg=resMap.get("ReturnMsg").toString();
                String returnMsgNo=resMap.get("ReturnMsgNo").toString();
                String tradeType=resMap.get("TradeType").toString();
                order.setAuthCode(authCode);
                order.setProductId(tradeType);
                order.setExtInfo(ext_info);
                order.setType(1);
                orderService.updateOrder(order);
                mv.addObject("authCode", authCode);
                mv.addObject("returnMsgNo", returnMsgNo);
                mv.addObject("returnMsg", returnMsg);
                mv.addObject("orderId", order.getId());
                logger.info("ingame author:" + authCode + "\torderid:" + order.getOrderId());
            } catch(Exception e) {
                logger.error(e);
                mv=new ModelAndView("error");
                mv.addObject("errorMsg", e.getMessage());
            }

        } else {
            mv=new ModelAndView("error");
            mv.addObject("errorMsg", "創建訂單失敗");
        }
        Integer cpId=RequestUtil.getInteger(request, Constants.CP_ID);
        Integer seqNum=RequestUtil.getInteger(request, Constants.SEQ_NUM);
        Integer serverId=RequestUtil.getInteger(request, "serverId");
        String uId=RequestUtil.getString(request, "uId");
        mv.addObject("cpId", cpId);
        mv.addObject("seqNum", seqNum);
        mv.addObject("serverId", serverId);
        mv.addObject("uId", uId);
        mv.addObject("orderId", order.getId());
        mv.addObject("ext_info", order.getExtInfo());
        return mv;
    }

    @RequestMapping("/mycard/ingame/submit")
    public ModelAndView submitCardInfo(HttpServletRequest request, HttpServletResponse response) throws YunPayException {
        ModelAndView mv=new ModelAndView("error");
        Integer orderId=RequestUtil.getInteger(request, "orderId");
        String authCode=RequestUtil.getString(request, "authCode");
        String cardId=RequestUtil.getString(request, "cardId");
        String cardPwd=RequestUtil.getString(request, "cardPwd");
        String faceId=SystemProperties.getProperty("mycard.ingame.facid");
        OrderTO order=orderService.getOrderById(orderId);
        if(order != null) {
            String key1=SystemProperties.getProperty("mycard.ingame.key1.gameid" + order.getGameId());
            String key2=SystemProperties.getProperty("mycard.ingame.key2.gameid" + order.getGameId());
            String tradeType=order.getProductId();
            String uid=order.getUserId();
            HashMap<String, String> params=new HashMap<String, String>();
            params.put("facId", faceId);
            params.put("authCode", authCode);
            params.put("facMemId", uid);
            params.put("cardId", cardId);
            params.put("cardPwd", cardPwd);
            try {
                StringBuilder hash=new StringBuilder();
                hash.append(key1).append(faceId).append(authCode).append(uid).append(cardId).append(cardPwd).append(key2);
                params.put("hash", MessageDigestUtil.getSHA256(hash.toString()));
                String paramsString=ParameterUtil.mapToUrl(params);
                String res=
                    HTTPUtil.httpGet(SystemProperties.getProperty("mycard.ingame.tradetype1.confirm.url"), paramsString, "utf-8");
                HashMap<String, Object> resMap=JsonUtil.Json2Object(res, new TypeReference<HashMap<String, Object>>() {
                });
                String ReturnMsgNo=resMap.get("ReturnMsgNo").toString();
                String ReturnMsg=resMap.get("ReturnMsg").toString();
                String CardKind=resMap.get("CardKind").toString();// 保存充值卡类型
                String CardPoint=resMap.get("CardPoint").toString();
                if(ReturnMsgNo.equals("1")) {
                    String SaveSeq=resMap.get("SaveSeq").toString();
                    String facTradeSeq=resMap.get("facTradeSeq").toString();
                    String oProjNo=resMap.get("oProjNo").toString(); // 保存活动状态吗
                    order=orderService.getOrderByOrderId(facTradeSeq);
                    logger.info("ingame confirm SaveSeq:" + SaveSeq + "\torderid:" + order.getOrderId() + "\treturnNo:"
                        + ReturnMsgNo);
                    if(order != null) {
                        order.setTradeNo(SaveSeq);
                        order.setAmount(Integer.valueOf(CardPoint) * 100);
                        order.setResultMsg(ReturnMsg);
                        order.setPaySuccess(Integer.valueOf(ReturnMsgNo));
                        order.setResultCode(ReturnMsgNo);
                        order.setAuthCode(authCode);
                        order.setProNo(oProjNo);
                        order.setCardNo(cardId);
                        order.setCardPwd(cardPwd);
                        orderService.updateOrderAndSendQueue(order);
                        mv.addObject("errorMsg", ReturnMsg);
                    }
                } else {
                    order.setResultMsg(ReturnMsg);
                    order.setPaySuccess(Integer.valueOf(ReturnMsgNo));
                    order.setResultCode(ReturnMsgNo);
                    order.setProductId(CardKind);
                    order.setCardNo(cardId);
                    order.setCardPwd(cardPwd);
                    orderService.updateOrder(order);
                    mv.addObject("errorMsg", ReturnMsgNo + ":" + ReturnMsg);
                }
            } catch(Exception e) {
                throw new YunPayException(e.getMessage());
            }
        } else {
            mv.addObject("errorMsg", "未知訂單");
        }
        return mv;
    }

    @RequestMapping("/mycard/billing/show")
    public ModelAndView billingShow(HttpServletRequest request, HttpServletResponse response) throws YunPayException {
        ModelAndView mv=new ModelAndView("billshow");
        Integer serverSeqNum=RequestUtil.getInteger(request, "serverId");
        Integer seqNum=RequestUtil.getInteger(request, Constants.SEQ_NUM);
        Integer cpId=RequestUtil.getInteger(request, Constants.CP_ID);
        String uid=RequestUtil.getString(request, "uId");
        Integer type=RequestUtil.getInteger(request, "type");
        String orderId=RequestUtil.getString(request, "orderId");
        String ext_info=RequestUtil.getString(request, "ext_info");
        GameTO game=GameCache.getGameTO(cpId, seqNum);
        Map<Integer, PayChannelTO> payments=PayChannelCache.getPayChannelList();
        mv.addObject("payments", payments);
        mv.addObject("serverSeqNum", serverSeqNum);
        mv.addObject("seqNum", seqNum);
        mv.addObject("cpId", cpId);
        mv.addObject("uId", uid);
        mv.addObject("type", type);
        mv.addObject("game", game);
        mv.addObject("orderId", orderId);
        mv.addObject("ext_info", ext_info);
        return mv;
    }
    
    @RequestMapping("/mycard/billing/productlist")
    public @ResponseBody
    List<PayChannelTO> getProductList(HttpServletRequest request, HttpServletResponse response) throws YunPayException {
        int paymentId=RequestUtil.getInteger(request, "paymentId");
        int gameId=RequestUtil.getInteger(request, "gameId");
        Map<String, PayChannelTO> payments=PayChannelCache.getPayServiceList(paymentId, gameId);
        Iterator<String> it=payments.keySet().iterator();
        List<PayChannelTO> list=new ArrayList<PayChannelTO>();
        while(it.hasNext()){
            list.add(payments.get(it.next()));
        }
        Collections.sort(list,new Comparator<PayChannelTO>(){  
            public int compare(PayChannelTO arg0, PayChannelTO arg1) {  
                return arg0.getOrder().compareTo(arg1.getOrder());  
            }  
        });  
        return list;
    }
    /*@RequestMapping("/mycard/billing/show")
    public ModelAndView billingShow(HttpServletRequest request, HttpServletResponse response) throws YunPayException {
        ModelAndView mv=new ModelAndView("billshow");
        Integer serverSeqNum=RequestUtil.getInteger(request, "serverId");
        Integer seqNum=RequestUtil.getInteger(request, Constants.SEQ_NUM);
        Integer cpId=RequestUtil.getInteger(request, Constants.CP_ID);
        String uid=RequestUtil.getString(request, "uId");
        Integer type=RequestUtil.getInteger(request, "type");
        String orderId=RequestUtil.getString(request, "orderId");
        String ext_info=RequestUtil.getString(request, "ext_info");
        GameTO game=GameCache.getGameTO(cpId, seqNum);
        String faceId=SystemProperties.getProperty("mycard.billing.faceid");
        String res=HTTPUtil.httpGet(SystemProperties.getProperty("mycard.billing.payment.query.url") + faceId, "", "utf-8");
        if(StringUtils.isNotBlank(res)) {
            res=res.replaceAll("<string .*?>", "").replaceAll("</string>", "");
            String[] payments=res.split(",");
            Map<String, String> payment=new HashMap<String, String>();
            String firstPaymentId="";
            for(int i=0; i < payments.length; i++) {
                String p=payments[i];
                String[] value=p.split("\\|");
                payment.put(value[0], value[1]);
                if(i == 0) {
                    firstPaymentId=value[0];
                }
            }
            if(StringUtils.isNotBlank(firstPaymentId)) {
                res=
                    HTTPUtil.httpGet(SystemProperties.getProperty("mycard.billing.product.price.query.url") + faceId + "/"
                        + firstPaymentId, "", "utf-8");
                if(StringUtils.isNotBlank(res)) {
                    String json=res.substring(1, res.length() - 1);
                    json=json.replaceAll("'", "\"");
                    HashMap<String, Object> jsonObj=JsonUtil.Json2Object(json, new TypeReference<HashMap<String, Object>>() {
                    });
                    List<Map<String, String>> productList=(List<Map<String, String>>)jsonObj.get("Table");
                    mv.addObject("productList", productList);
                }
            }
            mv.addObject("payments", payment);
            mv.addObject("serverSeqNum", serverSeqNum);
            mv.addObject("seqNum", seqNum);
            mv.addObject("cpId", cpId);
            mv.addObject("uId", uid);
            mv.addObject("type", type);
            mv.addObject("game", game);
            mv.addObject("orderId", orderId);
            mv.addObject("ext_info", ext_info);
        } else {
            mv.addObject("errorMsg", "獲取支付品項失敗，請重試！");
        }
        return mv;
    }*/

   /* @RequestMapping("/mycard/billing/productlist")
    public @ResponseBody
    List<Map<String, String>> getProductList(HttpServletRequest request, HttpServletResponse response) throws YunPayException {
        List<Map<String, String>> resData=null;
        String paymentId=RequestUtil.getString(request, "paymentId");
        String res=
            HTTPUtil.httpGet(
                SystemProperties.getProperty("mycard.billing.product.price.query.url")
                    + SystemProperties.getProperty("mycard.billing.faceid") + "/" + paymentId, "", "utf-8");
        try {
            if(StringUtils.isNotBlank(res)) {
                String json=res.substring(1, res.length() - 1);
                json=json.replaceAll("'", "\"");
                HashMap<String, Object> jsonObj=JsonUtil.Json2Object(json, new TypeReference<HashMap<String, Object>>() {
                });
                resData=(List<Map<String, String>>)jsonObj.get("Table");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return resData;
    }*/

    /**
     * 3.1 3.2步骤
     * @param request
     * @param response
     * @return
     * @throws YunPayException
     */
    @RequestMapping("/mycard/billing/author")
    public ModelAndView billingAuthor(HttpServletRequest request, HttpServletResponse response) throws YunPayException {
        String Serviceid=RequestUtil.getString(request, "Mycard_Serviceid");
        ModelAndView mv=new ModelAndView("error");
        if(StringUtils.isBlank(Serviceid)){
            mv.addObject("errorMsg", "请选择储值金额");
        }
        OrderTO order=createOrder(request);
        Integer amount=RequestUtil.getInteger(request, "amount");
        
        if(order != null) {
            String authurl=SystemProperties.getProperty("mycard.billing.auth.url");
            authurl=
                authurl.replace("$serverid", Serviceid).replace("$tradeSeq", order.getOrderId())
                    .replace("$paymentAmount", String.valueOf(amount));
            String res=HTTPUtil.httpGet(authurl, "", "utf-8");
            logger.info(res);
            try {
                res=res.replaceAll("<string .*?>", "").replaceAll("</string>", "");
                String[] resArr=res.split("\\|");
                String code=resArr[0];
                if(code.equals("1")) {
                    String trandNo=resArr[2];
                    String authCode=resArr[3];
                    order.setTradeNo(trandNo);
                    order.setAuthCode(authCode);
                    order.setType(2);
                    orderService.updateOrder(order);
                    response.sendRedirect(SystemProperties.getProperty("mycard.billing.confirm.url") + "?AuthCode=" + authCode);
                    return null;
                } else {
                    mv.addObject("errorMsg", "授權失敗，請重試！");
                }
            } catch(Exception e) {
                mv.addObject("errorMsg", "請求超時，請重試！");
                e.printStackTrace();
            }
        } else {
            mv.addObject("errorMsg", "創建訂單失敗");
        }
        return mv;
    }

    /**
     * 接收3.2的参数，3.3 3.4步骤 mycard，billing回调
     * @param request
     * @param response
     * @return
     * @throws YunPayException
     */
    @RequestMapping("/service/result")
    public ModelAndView billingResult(HttpServletRequest request, HttpServletResponse response) throws YunPayException {
        ModelAndView mv=new ModelAndView("error");
        String ReturnMsgNo=RequestUtil.getString(request, "ReturnMsgNo");
        String ReturnMsg=RequestUtil.getString(request, "ReturnMsg");
        String AuthCode=RequestUtil.getString(request, "AuthCode");
        String TradeSeq=RequestUtil.getString(request, "TradeSeq");
        if(ReturnMsgNo != null && ReturnMsgNo.equals("1")) {
            try {
                OrderTO order=orderService.getOrderByOrderId(TradeSeq);
                if(order != null) {
                    Map<String, String> params=new HashMap<String, String>();
                    params.put("AuthCode", AuthCode);
                    String res=
                        HTTPUtil.httpGet(SystemProperties.getProperty("mycard.billing.result.check.url"),
                            ParameterUtil.mapToUrl(params), "utf-8");// 3.3
                    logger.info(res);
                    res=res.replaceAll("<string .*?>", "").replaceAll("</string>", "");
                    String[] resArr=res.split("\\|");
                    String code=resArr[0];
                    String msg=resArr[1];
                    String status=resArr[2];
                    if(code.equals("1") && status.equals("3")) {
                        params.clear();
                        params.put("CPCustId", String.valueOf(order.getUserId()));
                        params.put("AuthCode", AuthCode);
                        res=
                            HTTPUtil.httpGet(SystemProperties.getProperty("mycard.billing.result.confirm.url"),
                                ParameterUtil.mapToUrl(params), "utf-8");// 3.4
                        res=res.replaceAll("<string .*?>", "").replaceAll("</string>", "");
                        resArr=res.split("\\|");
                        String code2=resArr[0];
                        String msg2=resArr[1];
                        if(code2.equals("1")) {
                            String tranno=resArr[2];
                            order.setTradeNo(tranno);
                        }
                        order.setPaySuccess(Integer.valueOf(code2));
                        order.setResultCode(code2);
                        order.setResultMsg(msg2);
                        orderService.updateOrderAndSendQueue(order);
                        mv.addObject("errorMsg", msg2);
                    } else {
                        mv.addObject("errorMsg", msg);
                    }
                } else {
                    mv.addObject("errorMsg", "未知訂單：" + TradeSeq);
                }
            } catch(Exception e) {
                logger.error(e);
            }
        } else {
            mv.addObject("errorMsg", ReturnMsg);
        }
        return mv;
    }

    /**
     * 補儲的url 没有完成3.3 3.4
     * @param request
     * @param response
     * @return
     * @throws YunPayException
     */
    @RequestMapping("/service/checkresult")
    public ModelAndView checkResult(HttpServletRequest request, HttpServletResponse response) throws YunPayException {
        ModelAndView mv=new ModelAndView("error");
        String data=RequestUtil.getString(request, "data");
        try {
            String TradeSeq=PayMentUtil.getXmlValue(data, "TradeSeq");
            String ReturnMsgNo=PayMentUtil.getXmlValue(data, "ReturnMsgNo");
            if(ReturnMsgNo != null && ReturnMsgNo.equals("1")) {
                OrderTO order=orderService.getOrderByOrderId(TradeSeq);
                if(order != null) {
                    Map<String, String> params=new HashMap<String, String>();
                    params.put("AuthCode", order.getAuthCode());
                    String res=
                        HTTPUtil.httpGet(SystemProperties.getProperty("mycard.billing.result.check.url"),
                            ParameterUtil.mapToUrl(params), "utf-8");
                    logger.info(res);
                    res=res.replaceAll("<string .*?>", "").replaceAll("</string>", "");
                    String[] resArr=res.split("\\|");
                    String code=resArr[0];
                    String msg=resArr[1];
                    String status=resArr[2];
                    if(code.equals("1") && status.equals("3")) {
                        params.clear();
                        params.put("CPCustId", String.valueOf(order.getUserId()));
                        params.put("AuthCode", order.getAuthCode());
                        res=
                            HTTPUtil.httpGet(SystemProperties.getProperty("mycard.billing.result.confirm.url"),
                                ParameterUtil.mapToUrl(params), "utf-8");
                        res=res.replaceAll("<string .*?>", "").replaceAll("</string>", "");
                        resArr=res.split("\\|");
                        String code2=resArr[0];
                        String msg2=resArr[1];
                        if(code2.equals("1")) {
                            String tranno=resArr[2];
                            order.setTradeNo(tranno);
                        }
                        order.setPaySuccess(Integer.valueOf(code2));
                        order.setResultCode(code2);
                        order.setResultMsg(msg2);
                        orderService.updateOrderAndSendQueue(order);
                        mv.addObject("errorMsg", msg2);
                    } else {
                        mv.addObject("errorMsg", msg);
                    }
                } else {
                    mv.addObject("errorMsg", "儲值成功，訂單號：" + TradeSeq);
                }
            }
        } catch(Exception e) {
            logger.error(e);
        }
        return mv;
    }

    private OrderTO createOrder(HttpServletRequest request) throws YunPayException {
        OrderTO order=null;
        String Mycard_Serviceid=RequestUtil.getString(request, "Mycard_Serviceid");
        Integer serverid=RequestUtil.getInteger(request, "serverId");
        Integer seqNum=RequestUtil.getInteger(request, Constants.SEQ_NUM);
        Integer amount=RequestUtil.getInteger(request, "amount");
        Integer cpId=RequestUtil.getInteger(request, Constants.CP_ID);
        Integer type=RequestUtil.getInteger(request, "type");
        String uid=RequestUtil.getString(request, "uId");
        String ext_info=RequestUtil.getString(request, "ext_info");
        String cardId=RequestUtil.getString(request, "cardId");
        String cardPwd=RequestUtil.getString(request, "cardPwd");
        GameTO game=GameCache.getGameTO(cpId, seqNum);
        if(game == null) {
            throw new YunPayException(ErrorCode.GAME_NOT_EXIST);
        }
        String id=RequestUtil.getString(request, "orderId");
        if(StringUtils.isNotBlank(id)) {
            order=orderService.getOrderById(Integer.valueOf(id));
            if(StringUtils.isNotBlank(Mycard_Serviceid)){
                order.setCardNo(Mycard_Serviceid);
            }
            if(amount != null && amount.intValue() != 0) {
                order.setAmount(amount * 100);
            }
            orderService.updateOrder(order);
        } else {
            order=new OrderTO();
            order.setCpId(game.getCpId());
            order.setType(type);
            if(StringUtils.isNotBlank(Mycard_Serviceid)){
                order.setCardNo(Mycard_Serviceid);
            }else{
                order.setCardNo(cardId);
            }
            order.setCardPwd(cardPwd);
            order.setGameId(game.getId());
            order.setGameServerId(serverid);
            order.setExtInfo(ext_info);
            if(amount != null && amount.intValue() != 0) {
                order.setAmount(amount * 100);
            }
            order.setUserId(uid);
            String orderid=PayMentUtil.genOrderId(String.valueOf(cpId), order.getGameId(), order.getGameServerId());
            order.setOrderId(orderid);
            orderService.addOrder(order);
        }

        return order;
    }

    @RequestMapping("/common/report")
    public void getReport(HttpServletRequest request, HttpServletResponse response) throws YunPayException {
        PrintWriter writer=null;
        try {
            writer=response.getWriter();
            StringBuilder stringBuilder=new StringBuilder();
            String splitStr=",";
            String StartDate=RequestUtil.getString(request, "StartDate");
            String EndDate=RequestUtil.getString(request, "EndDate");
            String cardno=RequestUtil.getString(request, "MyCardID");
            if(StringUtils.isEmpty(cardno) && StringUtils.isEmpty(StartDate) && StringUtils.isEmpty(EndDate)) {
                writer.write("請輸入查詢條件");
                return;
            }
            OrderCriteriaTO selectCriteria=new OrderCriteriaTO();
            selectCriteria.setCardNo(cardno);
            selectCriteria.setType(1);
            Date stDate=DateUtil.toDate(StartDate, "yyyy/MM/dd");
            selectCriteria.setStartDate(stDate);
            if(StringUtils.isBlank(EndDate) && StringUtils.isNotBlank(StartDate)) {
                selectCriteria.setEndDate(DateUtil.getBeforeDate(stDate, -1));
            }
            if(StringUtils.isNotBlank(EndDate)) {
                Date edDate=DateUtil.toDate(EndDate, "yyyy/MM/dd");
                selectCriteria.setEndDate(DateUtil.getBeforeDate(edDate, -1));
            }
            List<OrderTO> orders=orderService.getOrders(selectCriteria);
            if(orders != null) {
                for(OrderTO order: orders) {
                    stringBuilder.append(order.getCardNo()).append(splitStr).append(order.getUserId()).append(splitStr)
                        .append(order.getCardNo()).append(splitStr).append(order.getOrderId()).append(splitStr)
                        .append(DateUtil.formatDateTime(order.getResultDate())).append("<BR>");
                }
                writer.write(stringBuilder.toString());
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }

    }

}
