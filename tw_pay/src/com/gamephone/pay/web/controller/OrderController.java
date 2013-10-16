package com.gamephone.pay.web.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gamephone.common.Constants;
import com.gamephone.common.ErrorCode;
import com.gamephone.common.context.SystemProperties;
import com.gamephone.common.to.GameTO;
import com.gamephone.common.to.OrderTO;
import com.gamephone.common.to.ResponseTO;
import com.gamephone.common.util.RequestUtil;
import com.gamephone.pay.cache.GameCache;
import com.gamephone.pay.exception.YunPayException;
import com.gamephone.pay.service.OrderService;
import com.gamephone.pay.util.PayMentUtil;

@Controller
public class OrderController {

    private static final Logger logger=Logger.getLogger(OrderController.class);

    @Resource
    private OrderService orderService;
    
    @ResponseBody
    @RequestMapping("/service/order/create.html")
    public ResponseTO<OrderTO> createOrder(HttpServletRequest request) throws YunPayException {
        OrderTO order=null;
        String id=RequestUtil.getString(request, "orderId");
        Integer serverid=RequestUtil.getInteger(request, "serverId");
        Integer seqNum=RequestUtil.getInteger(request, Constants.SEQ_NUM);
        Integer amount=RequestUtil.getInteger(request, "amount");
        Integer cpId=RequestUtil.getInteger(request, Constants.CP_ID);
        Integer type=RequestUtil.getInteger(request, "type");
        String uid=RequestUtil.getString(request, "uId");
        String ext_info=RequestUtil.getString(request, "ext_info");
        String productId=RequestUtil.getString(request, "productId");
        GameTO game=GameCache.getGameTO(cpId, seqNum);
        if(game == null) {
            throw new YunPayException(ErrorCode.GAME_NOT_EXIST);
        }
        ResponseTO<OrderTO> res=new ResponseTO<OrderTO>();
        if(StringUtils.isNotBlank(id)) {
            logger.info("has order id:"+id);
            order=orderService.getOrderById(Integer.valueOf(id));
        } else {
            order=new OrderTO();
            order.setCpId(game.getCpId());
            order.setType(type);
            order.setProductId(productId);
            order.setGameId(game.getId());
            order.setGameServerId(serverid);
            order.setExtInfo(ext_info);
            if(amount != null && amount.intValue() != 0) {
                order.setAmount(amount * 100);
            }
            order.setUserId(uid);
            String orderid=PayMentUtil.genOrderId(String.valueOf(cpId), order.getGameId(), order.getGameServerId());
            logger.info("create order orderid:"+orderid);
            order.setOrderId(orderid);
            orderService.addOrder(order);
        }
        if(order != null && order.getId().intValue()!=0){
            res.setBusinessResult(order);
            res.setSuccess(true);
        }
        return res;
    }
    
}
