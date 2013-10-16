/**
 * $Id: MenuController.java,v 1.3 2012/07/13 07:33:02 jiayu.qiu Exp $
 */
package com.gamephone.admin.ui.web;

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
import com.gamephone.admin.common.service.MenuService;
import com.gamephone.admin.common.to.MenuTO;
import com.gamephone.admin.common.util.DwzJsonUtil;
import com.gamephone.common.util.RequestUtil;

/**
 * @author xianchao.sun@downjoy.com
 */
@Controller
@RequestMapping("/system/menu.html")
public class MenuController implements Constants {

    @Autowired
    private MenuService menuService;

    /**
     * @param request
     * @return java.lang.Long
     * @throws AdminException
     */
    @RequestMapping(params="act=list")
    public String listSystemMenus(HttpServletRequest request) throws AdminException {
        List<MenuTO> menus=menuService.getMenus();
        request.setAttribute("menus", menus);
        return "/modules/system/menu_list.jsp";
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
            MenuTO to=menuService.getMenuById(id);
            request.setAttribute("menuTO", to);
        }
        return "/modules/system/menu_editor.jsp";
    }

    /**
     * @param request
     * @param response
     * @return java.lang.Long
     * @throws AdminException
     */
    @RequestMapping(params="act=select")
    public String goSelect(HttpServletRequest request, HttpServletResponse response) throws AdminException {
        List<MenuTO> menus=menuService.getMenus();
        request.setAttribute("menus", menus);
        return "/modules/system/menu_select.jsp";
    }

    /**
     * @param request
     * @return java.lang.Long
     * @throws AdminException
     */
    @RequestMapping(params="act=save")
    public ModelAndView save(HttpServletRequest request) throws AdminException {
        Long id=RequestUtil.getLong(request, "id");
        String name=RequestUtil.getString(request, "name");
        Long parentId=RequestUtil.getLong(request, "parentId");
        String lcode=RequestUtil.getString(request, "parentIdPath");
        Integer sortOrderNo=RequestUtil.getInteger(request, "sortOrderNo");
        String url=RequestUtil.getString(request, "url");
        String remark=RequestUtil.getString(request, "remark");
        MenuTO to=new MenuTO();
        MenuTO parent=new MenuTO();
        to.setId(id);
        parent.setId(parentId);
        to.setParent(parent);
        to.setParentIdPath(lcode);
        to.setName(name);
        to.setSortOrderNo(sortOrderNo);
        to.setRemark(remark);
        to.setUrl(url);
        menuService.saveMenuTO(to);
        return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getOkStatusMsg(null));
    }

    /**
     * @param request
     * @return java.lang.Long
     * @throws AdminException
     */
    @RequestMapping(params="act=del")
    public ModelAndView deleteTreeNode(HttpServletRequest request) throws AdminException {
        String menuIds=RequestUtil.getString(request, "treecheckbox");
        if(StringUtils.isEmpty(menuIds)) {
            throw new AdminException("请选择要删除的节点！");
        }
        String[] menuIdArr=menuIds.split(",");
        for(String menuId: menuIdArr) {
            menuService.deleteMenu(Long.parseLong(menuId));
        }
        return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getOkStatusMsg(null));
    }
}
