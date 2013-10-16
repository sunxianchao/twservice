/**
 * $Id: SystemController.java,v 1.6 2012/07/13 07:33:02 jiayu.qiu Exp $
 */
package com.gamephone.admin.ui.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.gamephone.admin.common.Constants;
import com.gamephone.admin.common.criteria.GameCriteriaTO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.admin.common.service.GameService;
import com.gamephone.admin.common.service.MenuService;
import com.gamephone.admin.common.service.SystemService;
import com.gamephone.admin.common.to.AdminUserTO;
import com.gamephone.admin.common.to.MenuTO;
import com.gamephone.admin.common.util.DwzJsonUtil;
import com.gamephone.admin.common.web.Funcs;
import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.GameTO;
import com.gamephone.common.util.RequestUtil;

/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-3-23
 */
@Controller
public class SystemController implements Constants {

    private static final String USER_LOGIN="/login.jsp";

    @Autowired
    private SystemService systemService;

    @Autowired
    private MenuService menuService;
    
    @Autowired
    private GameService gameService;

    @RequestMapping("/index.html")
    public String index(HttpServletRequest request) throws AdminException {
        AdminUserTO user=Funcs.getSessionUser(request.getSession());
        List<MenuTO> menus=menuService.getUserMenus(user);
        request.setAttribute("menus", menus);
        return "/index.jsp";
    }

    @RequestMapping("/login.html")
    public String login() {
        return USER_LOGIN;
    }

    @RequestMapping("/dologin.html")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws AdminException {
        String email=RequestUtil.getString(request, "email");
        String password=RequestUtil.getString(request, "password");
        if(StringUtils.isBlank(email)) {
            throw new AdminException("请填写用户名！");
        }
        if(StringUtils.isBlank(password)) {
            throw new AdminException("请填写密码！");
        }
        try {
            AdminUserTO user=systemService.doLogin(email, password);
            request.getSession().setAttribute(SESSION_USER, user);
            SearchPagerModel<GameTO> games=gameService.getGameByCriteria(new GameCriteriaTO(), 0, com.gamephone.admin.Constants.PAGESIZE);
            request.getSession().setAttribute("games", games.getResultList());
        } catch(AdminException ex) {
            String errorMsg=ex.getMessage();
            if(RequestUtil.isAjax(request)) {
                return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getErrorStatusMsg(errorMsg));
            } else {
                Map<String, Object> model=new HashMap<String, Object>();
                model.put("loginErrInfo", errorMsg);
                return new ModelAndView(USER_LOGIN, "model", model);
            }
        }
        if(RequestUtil.isAjax(request)) {
            return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getOkStatusMsg(null));
        } else {
            return new ModelAndView(new RedirectView(request.getContextPath() + "/"), "model", null);
        }
    }

    @RequestMapping("/login_dialog.html")
    public String loginDialog() {
        return "/login_dialog.jsp";
    }

    @RequestMapping("/logout.html")
    public String logout(HttpServletRequest request) {
        HttpSession session=request.getSession();
        session.removeAttribute(SESSION_USER);
        session.invalidate();
        return USER_LOGIN;
    }
}
