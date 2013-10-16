/**
 * $Id: UserMenuController.java,v 1.3 2012/07/13 07:33:02 jiayu.qiu Exp $
 */
package com.gamephone.admin.ui.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gamephone.admin.common.Constants;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.admin.common.service.AdminUserService;
import com.gamephone.admin.common.service.MenuService;
import com.gamephone.admin.common.to.AdminUserTO;
import com.gamephone.admin.common.to.MenuTO;
import com.gamephone.admin.common.util.DwzJsonUtil;
import com.gamephone.common.util.RequestUtil;

/**
 * @author xianchao.sun@downjoy.com
 */
@Controller
@RequestMapping("/system/user_menu.html")
public class UserMenuController implements Constants {

    @Autowired
    private MenuService menuService;

    @Autowired
    private AdminUserService adminUserService;

    /**
     * @param request
     * @param response
     * @return java.lang.Long
     * @throws AdminException
     */
    @RequestMapping(params="act=list")
    public String listSystemMenus(HttpServletRequest request, HttpServletResponse response) throws AdminException {
        Long userId=RequestUtil.getLong(request, "userid");
        AdminUserTO user=adminUserService.getAdminUserById(userId);
        if(null == user) {
            throw new AdminException("用户不存在！");
        }
        List<MenuTO> menus=menuService.getMenus();
        List<MenuTO> userMenus=menuService.getUserMenus(user);
        List<Long> userMenuIds=new ArrayList<Long>();
        for(MenuTO menu: userMenus) {
            userMenuIds.add(menu.getId());
        }
        request.setAttribute("menus", menus);
        request.setAttribute("userMenuIds", userMenuIds);
        return "/modules/system/user_menu_list.jsp";
    }

    /**
     * @param request
     * @return ModelAndView
     * @throws AdminException
     */
    @RequestMapping(params="act=save")
    public ModelAndView save(HttpServletRequest request) throws AdminException {
        Long userId=RequestUtil.getLong(request, "userid");
        String menuIdStr=RequestUtil.getString(request, "treecheckbox");
        if(null == userId || userId <= 0 || StringUtils.isBlank(menuIdStr)) {
            throw new AdminException("非法操作！");
        }
        AdminUserTO user=adminUserService.getAdminUserById(userId);
        if(null == user) {
            throw new AdminException("用户不存在！");
        }
        String[] ids=menuIdStr.split(",");
        List<Long> menuIds=new ArrayList<Long>(ids.length);
        for(int i=0; i < ids.length; i++) {
            menuIds.add(Long.valueOf(ids[i]));
        }

        menuService.addUserMenuTOs(user, menuIds);
        return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getOkStatusMsg("操作成功"));
    }
}
