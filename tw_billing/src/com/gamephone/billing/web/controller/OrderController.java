package com.gamephone.billing.web.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gamephone.billing.exception.BillingException;
import com.gamephone.billing.model.Order;
import com.gamephone.billing.service.OrderService;
import com.gamephone.billing.util.PayMentUtil;
import com.gamephone.common.Constants;
import com.gamephone.common.cache.GameCacheRead;
import com.gamephone.common.context.SystemProperties;
import com.gamephone.common.to.GameTO;
import com.gamephone.common.to.OnlineUser;
import com.gamephone.common.to.Result;
import com.gamephone.common.util.RequestUtil;

@Controller
public class OrderController {

    private static final Logger logger=Logger.getLogger(OrderController.class);

    @Resource
    private OrderService orderService;
    
    @Autowired
    private GameCacheRead gameCacheRead;
    
    @ResponseBody
    @RequestMapping("/service/order/create")
    public Result<Order> createOrder(HttpServletRequest request) throws BillingException {
        Order order=null;
        String id=RequestUtil.getString(request, "orderId");
        Integer amount=RequestUtil.getInteger(request, "amount");
        Integer type=RequestUtil.getInteger(request, "type");
        Integer gameId=RequestUtil.getInteger(request, "gameId");
        String extInfo=RequestUtil.getString(request, "extInfo");
        String productId=RequestUtil.getString(request, "productId");
        GameTO game=gameCacheRead.getGameById(gameId);
        if(game == null) {
            throw new BillingException(SystemProperties.getProperty("game.is.not.exist"));
        }
        OnlineUser onlineUser=(OnlineUser)request.getAttribute(Constants.ONLINE_USER);
        if(onlineUser == null){
            throw new BillingException(SystemProperties.getProperty("account.exception"));
        }
        if(onlineUser.getServerId() == null ||onlineUser.getServerId().intValue() == 0){
            throw new BillingException(SystemProperties.getProperty("gameserver.must.not.null"));
        }
        Result<Order> res=new Result<Order>();
        if(StringUtils.isNotBlank(id)) {
            logger.info("has order id:"+id);
            order=orderService.getOrderById(Integer.valueOf(id));
        } else {
            order=new Order();
            order.setCpId(game.getCpId());
            order.setType(type);
            order.setProductId(productId);
            order.setGameId(game.getId());
            order.setGameServerId(onlineUser.getServerId());
            order.setExtInfo(extInfo);
            if(amount != null && amount.intValue() != 0) {
                order.setAmount(amount * 100);
            }
            order.setUserId(onlineUser.getUserId());
            String orderid=PayMentUtil.genOrderId(String.valueOf(game.getCpId()), order.getGameId(), order.getGameServerId());
            logger.info("create order orderid:"+orderid);
            order.setOrderId(orderid);
            orderService.createOrder(order);
        }
        if(order != null && order.getId().intValue()!=0){
            res.setBusinessResult(order);
            res.setSuccess(true);
        }
        return res;
    }
    
}
