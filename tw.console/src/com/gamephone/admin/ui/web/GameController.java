/**
 * $Id: GameController.java,v 1.7 2012/07/13 07:33:02 jiayu.qiu Exp $
 */
package com.gamephone.admin.ui.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gamephone.admin.common.Constants;
import com.gamephone.admin.common.criteria.GameCriteriaTO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.admin.common.service.GameService;
import com.gamephone.admin.common.util.DwzJsonUtil;
import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.GameTO;
import com.gamephone.common.util.RequestUtil;

/**
 * 
 * @author lili.meng@downjoy.com
 *
 */
@Controller
@RequestMapping("/game.html")
public class GameController implements Constants {

    @Autowired
    private GameService gameService;
    
    private int pageSize=20;
    
    /**
     * @param request
     * @return java.lang.Long
     * @throws AdminException
     */
    @RequestMapping(params="act=list")
    public String listSystemMenus(HttpServletRequest request) throws AdminException {
        Integer pageNo=RequestUtil.getInteger(request, "pn");
        Integer id=RequestUtil.getInteger(request, "id");
        String name=RequestUtil.getString(request, "name");
        Integer status=RequestUtil.getInteger(request, "status");
        GameCriteriaTO criteria=new GameCriteriaTO();
        if(id != null) {
            criteria.setId(id);
        }
        if(name != null) {
            criteria.setName(name);
        }
        if(status != null) {
            criteria.setStatus(status);
        }
        if(pageNo == null) { // 判断页码是否为空
            pageNo=1;
        }
        SearchPagerModel<GameTO> games=gameService.getGameByCriteria(criteria, pageNo, pageSize);
        request.setAttribute("games", games);
        
        return "/modules/game/list.jsp";
    }
    
    /**
     * @param request
     * @param response
     * @return java.lang.Long
     * @throws AdminException
     */
    @RequestMapping(params="act=goedit")
    public String goEditor(HttpServletRequest request, HttpServletResponse response) throws AdminException {
        Long id=RequestUtil.getLong(request, "id");
        if(null != id) {
            GameTO game=gameService.getGameById(id);
            request.setAttribute("game", game);
        }
        return "/modules/game/editor.jsp";
    }

    /**
     * @param request
     * @return java.lang.Long
     * @throws AdminException
     */
    @RequestMapping(params="act=save")
    public ModelAndView save(HttpServletRequest request) throws AdminException {
        String name=RequestUtil.getString(request, "name");
        Integer cpId=RequestUtil.getInteger(request, "cpid");
        Integer seqNum=RequestUtil.getInteger(request, "seqnum");
        Integer id=RequestUtil.getInteger(request, "id");
        String secretKey=RequestUtil.getString(request, "secretKey");
        String notifyKey=RequestUtil.getString(request, "notifyKey");
        String notifyUrl =RequestUtil.getString(request, "notifyUrl");
        Integer status = RequestUtil.getInteger(request, "status");
        if(cpId==null){
            throw new AdminException("请选择厂商");
        }
        GameTO game=new GameTO();
        game.setId(id);
        game.setName(name);
        game.setCpId(cpId);
        game.setSeqNum(seqNum);
        game.setSecretKey(secretKey);
        game.setNotifyKey(notifyKey);
        game.setNotifyUrl(notifyUrl);
        game.setStatus(status);
        gameService.saveGame(game);
        return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getOkStatusMsg(null));
    }

    /**
     * @param request
     * @return java.lang.Long
     * @throws AdminException
     */
    @RequestMapping(params="act=change")
    public ModelAndView changeStatus(HttpServletRequest request) throws AdminException {
        Integer id=RequestUtil.getInteger(request, "id");
        Integer status=RequestUtil.getInteger(request, "status");
        if(status != null) {
            gameService.changeStatus(id, status);
        }
        return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getOkStatusMsg(null));
    }
}
