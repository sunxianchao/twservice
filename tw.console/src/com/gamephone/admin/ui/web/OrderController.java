package com.gamephone.admin.ui.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gamephone.admin.Constants;
import com.gamephone.admin.common.criteria.OrderCriteriaTO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.admin.common.service.OrderService;
import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.OrderTO;
import com.gamephone.common.util.RequestUtil;

@Controller
@RequestMapping("/order.html")
public class OrderController {

    private static final Logger logger=Logger.getLogger(OrderController.class);
    
    @Autowired
    private OrderService orderService;
    
    @RequestMapping(params="act=list")
    public String orderList(HttpServletRequest request) throws AdminException {
        Integer pn=RequestUtil.getInteger(request, "pageNum");
        Integer gameId=RequestUtil.getInteger(request, "gameId");
        String userId=RequestUtil.getString(request, "userId");
        Integer paySuccess=RequestUtil.getInteger(request, "paySuccess");
        Integer payChannelId=RequestUtil.getInteger(request, "payChannelId");
        Date startDate=RequestUtil.getDate(request, "startDate", "yyyy-MM-dd");
        Date endDate=RequestUtil.getDate(request, "endDate", "yyyy-MM-dd");
        String orderId=RequestUtil.getString(request, "orderId");
        String extInfo=RequestUtil.getString(request, "extInfo");
        OrderCriteriaTO orderCriteria=new OrderCriteriaTO();
        orderCriteria.setUserId(userId);
        orderCriteria.setGameId(gameId);
        orderCriteria.setPaySuccess(paySuccess);
        orderCriteria.setType(payChannelId);
        orderCriteria.setStartDate(startDate);
        orderCriteria.setEndDate(endDate);
        orderCriteria.setOrderId(orderId);
        orderCriteria.setExtInfo(extInfo);
        SearchPagerModel<OrderTO> pageModel=new SearchPagerModel<OrderTO>(null == pn ? 1 : pn, Constants.PAGESIZE);
        orderCriteria.setPageModel(pageModel);
        SearchPagerModel<OrderTO> result=orderService.getOrderList(orderCriteria);
        
        Integer totalAmount=orderService.getTotalAmount(orderCriteria);
        request.setAttribute("result", result);
        request.setAttribute("totalAmount", totalAmount);
        return "/modules/order/orderlist.jsp";
    }
    
    @RequestMapping(params="act=goupdate")
    public String goupdate(HttpServletRequest request) throws AdminException {
        Integer id=RequestUtil.getInteger(request, "id");
        OrderTO order=orderService.getOrderById(id);
        request.setAttribute("order", order);
        return "/modules/order/orderupdate.jsp";
    }
}
