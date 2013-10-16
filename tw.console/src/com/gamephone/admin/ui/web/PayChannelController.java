package com.gamephone.admin.ui.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gamephone.admin.common.Constants;
import com.gamephone.admin.common.criteria.PayChannelCriteriaTO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.admin.common.service.PayChannelService;
import com.gamephone.admin.common.util.DwzJsonUtil;
import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.OrderTO;
import com.gamephone.common.to.PayChannelTO;
import com.gamephone.common.util.RequestUtil;


@Controller
@RequestMapping("/pay.html")
public class PayChannelController  implements Constants{

    private static final Logger logger=Logger.getLogger(PayChannelController.class);
    
    @Autowired
    private PayChannelService payChannelService; 
    
    @RequestMapping(params="act=paytypes")
    public String getPayChannels(HttpServletRequest request, @RequestParam(value="pid", defaultValue="0", required=true) Integer pid) throws AdminException{
        Integer pageNum=RequestUtil.getInteger(request, "pageNum");
        PayChannelCriteriaTO criteriaTO=new PayChannelCriteriaTO();
        SearchPagerModel<PayChannelTO> searchPagerModel=new SearchPagerModel<PayChannelTO>(null == pageNum ? 1 : pageNum, Constants.PAGESIZE);
        criteriaTO.setPageModel(searchPagerModel);
        criteriaTO.setPid(pid);
        searchPagerModel=payChannelService.getPayChannels(criteriaTO);
        request.setAttribute("result", searchPagerModel);
        return "/modules/pay/paytypes.jsp";
    }
    
    @RequestMapping(params="act=goupdate")
    public String goupdate(HttpServletRequest request) throws AdminException {
        Integer id=RequestUtil.getInteger(request, "id");
        PayChannelCriteriaTO criteriaTO=new PayChannelCriteriaTO();
        SearchPagerModel<PayChannelTO> searchPagerModel=new SearchPagerModel<PayChannelTO>(1, Constants.PAGESIZE);
        criteriaTO.setPageModel(searchPagerModel);
        criteriaTO.setId(id);
        SearchPagerModel<PayChannelTO> payChannels=payChannelService.getPayChannels(criteriaTO);
        if(payChannels != null && payChannels.getResultList() != null && payChannels.getResultList().size()>0){
            request.setAttribute("payChannel", payChannels.getResultList().get(0));
        }
        return "/modules/pay/payupdate.jsp";
    }
    
    @RequestMapping(params="act=save")
    public ModelAndView save(HttpServletRequest request) throws AdminException {
        PayChannelTO channel=new PayChannelTO();
        Integer id=RequestUtil.getInteger(request, "id");
        String payName=RequestUtil.getString(request, "payName");
        Integer isLocal=RequestUtil.getInteger(request, "isLocal");
        Integer status=RequestUtil.getInteger(request, "status");
        Integer order=RequestUtil.getInteger(request, "order");
        channel.setPayName(payName);
        channel.setIsLocal(isLocal);
        if(id != null){
            channel.setId(id);
            channel.setStatus(status);
            channel.setOrder(order);
            payChannelService.updatePayChannelStatus(channel);
            //更新子节点
            PayChannelTO childChannel=new PayChannelTO();
            childChannel.setStatus(status);
            childChannel.setPid(id);
            payChannelService.updatePayChannelStatus(childChannel);
        }else{
            channel.setStatus(1);
            payChannelService.addParentPayChannel(channel);
        }
        
        return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getOkStatusMsg("操作成功"));
    }
    
    @RequestMapping(params="act=goadd")
    public String goadd(HttpServletRequest request) throws AdminException {
        return "/modules/pay/payupdate.jsp";
    }
    
    
    @RequestMapping(params="act=services")
    public String payServices(HttpServletRequest request) throws AdminException {
        getPayChannels(request, 0);
        Integer pageNum=RequestUtil.getInteger(request, "pageNum");
        PayChannelCriteriaTO criteriaTO=new PayChannelCriteriaTO();
        SearchPagerModel<PayChannelTO> searchPagerModel=new SearchPagerModel<PayChannelTO>(null == pageNum ? 1 : pageNum, Constants.PAGESIZE);
        Integer pid=RequestUtil.getInteger(request, "pid");
        Integer gameId=RequestUtil.getInteger(request, "gameId");
        criteriaTO.setPageModel(searchPagerModel);
        if(pid != null){
            criteriaTO.setPid(pid);
        }
        if(gameId != null){
            criteriaTO.setGameId(gameId);
            SearchPagerModel<PayChannelTO> payChannels=payChannelService.getPayChannels(criteriaTO);
            request.setAttribute("services", payChannels);
        }
        
        return "/modules/pay/payservices.jsp";
    }
    
    @RequestMapping(params="act=servicegoadd")
    public String payAddService(HttpServletRequest request) throws AdminException {
        getPayChannels(request, 0);
        return "/modules/pay/payserviceupdate.jsp";
    }
    
    @RequestMapping(params="act=serviceupdate")
    public String payUpdateService(HttpServletRequest request) throws AdminException {
        getPayChannels(request, 0);
        Integer id=RequestUtil.getInteger(request, "id");
        PayChannelCriteriaTO criteriaTO=new PayChannelCriteriaTO();
        SearchPagerModel<PayChannelTO> searchPagerModel=new SearchPagerModel<PayChannelTO>(1, Constants.PAGESIZE);
        criteriaTO.setPageModel(searchPagerModel);
        criteriaTO.setId(id);
        SearchPagerModel<PayChannelTO> payChannels=payChannelService.getPayChannels(criteriaTO);
        if(payChannels != null && payChannels.getResultList() != null && payChannels.getResultList().size()>0){
            request.setAttribute("payChannel", payChannels.getResultList().get(0));
        }
        return "/modules/pay/payserviceupdate.jsp";
    }
    
    @RequestMapping(params="act=servicesave")
    public ModelAndView payServiceSave(HttpServletRequest request) throws AdminException {
        PayChannelTO channel=new PayChannelTO();
        Integer id=RequestUtil.getInteger(request, "id");
        Integer gameId=RequestUtil.getInteger(request, "gameId");
        String payName=RequestUtil.getString(request, "payNameVal");
        Integer pid=RequestUtil.getInteger(request, "payName");
        String serverName=RequestUtil.getString(request, "serverName");
        String serverId=RequestUtil.getString(request, "serverId");
        Integer amount=RequestUtil.getInteger(request, "amount");
        Integer status=RequestUtil.getInteger(request, "status");
        Integer order=RequestUtil.getInteger(request, "order");
        
        channel.setPayName(payName);
        channel.setPid(pid);
        channel.setServerName(serverName);
        channel.setServerId(serverId);
        channel.setAmount(amount);
        channel.setGameId(gameId);
        if(id != null){
            channel.setId(id);
            channel.setStatus(status);
            channel.setOrder(order);
            payChannelService.updatePayChannelStatus(channel);
        }else{
            channel.setStatus(1);
            payChannelService.addChildPayChannel(channel);
        }
        
        return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getOkStatusMsg("操作成功"));
    }
}
