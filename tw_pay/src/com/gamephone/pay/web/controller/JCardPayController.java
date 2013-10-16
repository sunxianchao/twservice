package com.gamephone.pay.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gamephone.common.Constants;
import com.gamephone.common.ErrorCode;
import com.gamephone.common.to.GameTO;
import com.gamephone.common.to.OrderTO;
import com.gamephone.common.util.Base64;
import com.gamephone.common.util.MessageDigestUtil;
import com.gamephone.common.util.RequestUtil;
import com.gamephone.pay.PayConfig;
import com.gamephone.pay.cache.GameCache;
import com.gamephone.pay.exception.YunPayException;
import com.gamephone.pay.service.OrderService;
import com.gamephone.pay.util.PayMentUtil;

@Controller
public class JCardPayController {

    private static Map<String, String> serviceCodes=new HashMap<String, String>();
    static{
        serviceCodes.put("gameId_8", PayConfig.JCARD_SERVICE_CODE);// 台湾神将游戏id=8
    }

    @Resource
    private OrderService orderService;
    
    private static final Logger logger=Logger.getLogger(JCardPayController.class);
    
    @RequestMapping("/service/jcard/dopay.html")
    public ModelAndView doPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        OrderTO order=new OrderTO();
        Integer serverSeqNum=RequestUtil.getInteger(request, "serverid");
        String extInfo=RequestUtil.getString(request, "extinfo");
        Integer seqNum=RequestUtil.getInteger(request, Constants.SEQ_NUM);
        Integer amount=RequestUtil.getInteger(request, "amount");
        Integer cpId=RequestUtil.getInteger(request, Constants.CP_ID);
        String userid=RequestUtil.getString(request, "userId");
        order.setCpId(cpId);
        GameTO game=GameCache.getGameTO(cpId, seqNum);
        if(game == null){
            throw new YunPayException(ErrorCode.GAME_NOT_EXIST);
        }
        order.setGameId(game.getId());
        order.setUserId(userid);
        order.setGameServerId(serverSeqNum);
        String orderid=PayMentUtil.genOrderId(String.valueOf(cpId), order.getGameId(), order.getGameServerId());
        order.setOrderId(orderid);
        order.setExtInfo(extInfo);
        if(extInfo == null) extInfo="";
        extInfo=extInfo+ "|" +game.getId();//金流支付不支持特殊字符和中文（特殊字符使用"|()" ）
        orderService.addOrder(order);
        StringBuilder params=new StringBuilder();
        String sign=MessageDigestUtil.getMD5(order.getOrderId()+new String(Base64.decode(PayConfig.JCARD_PAY_KEY)).toString());
        params.append("ServiceCode=").append(serviceCodes.get("gameId_"+game.getId())).append("&")
              .append("OrderID=").append(order.getOrderId()).append("&")
              .append("ReturnURL=").append(PayConfig.JCARD_CALLBACK_URL).append("&")
              .append("UserID=").append(order.getUserId()).append("&")
              .append("Memo=").append(extInfo).append("&")
              .append("SignCode=").append(sign);
        
        String redirectUrl=PayConfig.JCARD_PAY_URL + "?"+ params.toString();
        logger.debug("jcard url:" + redirectUrl);
        response.sendRedirect(redirectUrl);
        return null;
    }
    
    @RequestMapping("/common/jcard/notify.html")
    public void callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("jcard callback-----------------------");
        Integer Flag=RequestUtil.getInteger(request, "Flag");
        String Outstring=RequestUtil.getString(request, "Outstring");
        String SvrType=RequestUtil.getString(request, "SvrType");
        String OrderID=RequestUtil.getString(request, "OrderID");
        String TransactionID=RequestUtil.getString(request, "TransactionID");
        String Points=RequestUtil.getString(request, "Points");
        String Memo=RequestUtil.getString(request, "Memo");
        String SignCode=RequestUtil.getString(request, "SignCode");
        String[] tmp=Memo.split("|");
        String gameid=tmp[tmp.length-1];
        String sign=MessageDigestUtil.getMD5(OrderID + "&" + new String(Base64.decode(PayConfig.JCARD_PAY_KEY)).toString() + "_" + TransactionID + "&" + serviceCodes.get("gameId_"+gameid) + "_" + Points);
        if(sign.equalsIgnoreCase(SignCode)){
            OrderTO order=orderService.getOrderByOrderId(OrderID);
            if(order != null){
                String resMsg=Outstring;
                if(Flag==1){//订单成功
                    resMsg=PayConfig.PAY_SUCCESS_RESULT_MSG;
                }
                order.setAmount(Integer.valueOf(Points));
                order.setTradeNo(TransactionID);
                order.setPaySuccess(Flag);
                order.setResultCode(String.valueOf(Flag));
                order.setResultMsg(resMsg);
                orderService.updateOrder(order);
            }
        }else{
            throw new YunPayException("md5 验证失败");
        }
    }
}
