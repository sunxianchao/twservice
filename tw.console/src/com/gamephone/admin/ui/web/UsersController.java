/**
 * $Id: UsersController.java,v 1.4 2012/07/13 07:33:02 jiayu.qiu Exp $
 */
package com.gamephone.admin.ui.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gamephone.admin.common.Constants;
import com.gamephone.admin.common.criteria.AdminUserSearchCriteriaTO;
import com.gamephone.admin.common.exception.AdminException;
import com.gamephone.admin.common.service.AdminUserService;
import com.gamephone.admin.common.service.GameService;
import com.gamephone.admin.common.to.AdminUserTO;
import com.gamephone.admin.common.util.DwzJsonUtil;
import com.gamephone.admin.common.web.Funcs;
import com.gamephone.common.criteria.SearchPagerModel;
import com.gamephone.common.to.GameTO;
import com.gamephone.common.type.StatusType;
import com.gamephone.common.type.YesNoType;
import com.gamephone.common.util.MessageDigestUtil;
import com.gamephone.common.util.RequestUtil;

/**
 * @author xianchao.sun@downjoy.com
 */

@Controller
@RequestMapping("/system/users.html") 
public class UsersController implements Constants {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private GameService gameService;
    
    public static final String USERS_LIST="/modules/system/user_list.jsp";

    public static final String ADD_USER="/modules/system/user_add.jsp";

    public static final String UPDATE_USER="/modules/system/user_update.jsp";

    /**
     * 用户详情列表
     * @param request
     * @param response
     * @return ModelAndView
     * @throws AdminException
     */
    @RequestMapping(params="act=list")
    public ModelAndView userList(HttpServletRequest request, HttpServletResponse response) throws AdminException {
        AdminUserTO to=Funcs.getSessionUser(request.getSession());
        if(to.getIsadmin().getId() == 0) {// 判断用户身份是否为管理员
            throw new AdminException("您不是管理员");
        }

        Long id=RequestUtil.getLong(request, "id");
        String realname=RequestUtil.getString(request, "realname");
        String email=RequestUtil.getString(request, "email");
        Integer pageNum=RequestUtil.getInteger(request, "pageNum");
        
        AdminUserSearchCriteriaTO criteriaTO=new AdminUserSearchCriteriaTO();
        SearchPagerModel<AdminUserTO> searchPagerModel=new SearchPagerModel<AdminUserTO>(null == pageNum ? 1 : pageNum, Constants.PAGESIZE);
        criteriaTO.setPageModel(searchPagerModel);
        criteriaTO.setId(id);
        criteriaTO.setEmail(email);
        criteriaTO.setRealname(realname);
        searchPagerModel=adminUserService.getAdminUsers(criteriaTO);
        Map<String, GameTO> gameMap=gameService.getAllGameasMap();
        for(AdminUserTO user : searchPagerModel.getResultList()){
            String gameName="";
            String ids=user.getGameids();
            if(StringUtils.isEmpty(ids)){
                continue;
            }
            String[] idarr=ids.split(",");
            for(String gid : idarr){
                gameName+=gameMap.get(gid).getName()+" ";
            }
            user.setGameids(gameName);
        }
        request.setAttribute("userListPager", searchPagerModel);
        return new ModelAndView(USERS_LIST);
    }

    /**
     * 删除用户
     * @param request
     * @param response
     * @return ModelAndView
     * @throws AdminException
     */
    @RequestMapping(params="act=del")
    public ModelAndView delUser(HttpServletRequest request, HttpServletResponse response) throws AdminException {
        Long userId=RequestUtil.getLong(request, "id");
        adminUserService.delAdminUser(userId);
        return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getOkStatusMsg(null));
    }

    /**
     * 输入新用户详细资料
     * @param request
     * @param response
     * @return ModelAndView
     * @throws AdminException
     */
    @RequestMapping(params="act=goadd")
    public ModelAndView goAddUser(HttpServletRequest request, HttpServletResponse response) throws AdminException {
        List<GameTO> games=gameService.getAllGames();
        return new ModelAndView(ADD_USER, "games", games);
    }

    /**
     * 输入老用户详细资料
     * @param request
     * @param response
     * @return ModelAndView
     * @throws AdminException
     */
    @RequestMapping(params="act=goupdate")
    public ModelAndView goUpdateUser(HttpServletRequest request, HttpServletResponse response) throws AdminException {
        Long userId=RequestUtil.getLong(request, "id");
        AdminUserTO userTO=adminUserService.getAdminUserById(userId);
        Map<String, Object> model=new HashMap<String, Object>();
        model.put("user", userTO);
        Map<String, GameTO> gameMap=gameService.getAllGameasMap();
        model.put("games", gameMap);
        String gameNames="";
        
        if(StringUtils.isNotEmpty(userTO.getGameids())){
            String[] idarr=userTO.getGameids().split(",");
            for(String gid : idarr){
                gameNames+=gameMap.get(gid).getName()+",";
            }
            model.put("gameNames", gameNames.substring(0, gameNames.length()-1));
        }
        
        return new ModelAndView(UPDATE_USER, "model", model);
    }

    /**
     * 添加新用户
     * @param request
     * @param response
     * @return ModelAndView
     * @throws AdminException
     */
    @RequestMapping(params="act=add")
    public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response) throws AdminException {
        String email=RequestUtil.getString(request, "email");
        String realname=RequestUtil.getString(request, "name");
        String password=RequestUtil.getString(request, "passward");
        String gameids=RequestUtil.getString(request, "gameids");
        String promptids=RequestUtil.getString(request, "promptids");
        try {
            password=MessageDigestUtil.getMD5(password + Constants.PASSWORD_SALT_KEY);
        } catch(Exception e) {
            e.printStackTrace();
        }
        Integer isadmin=Integer.valueOf(request.getParameter("isadmin"));
        AdminUserSearchCriteriaTO criteriaTO=new AdminUserSearchCriteriaTO();
        criteriaTO.setEmail(email);
        if(adminUserService.getAdminUsersCount(criteriaTO) > 0) {// 判断用户是否已经存在
            throw new AdminException("邮箱名已用");
        }
        AdminUserTO userTO=new AdminUserTO();
        userTO.setEmail(email);
        userTO.setRealname(realname);
        userTO.setPassword(password);
        userTO.setIsadmin(YesNoType.getById(isadmin));
        userTO.setGameids(gameids);
        userTO.setPromptids(promptids);
        adminUserService.addAdminUser(userTO);
        return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getOkStatusMsg(null));
    }

    /**
     * 更新老用户
     * @param request
     * @param response
     * @return ModelAndView
     * @throws AdminException
     */
    @RequestMapping(params="act=update")
    public ModelAndView updateUser(HttpServletRequest request, HttpServletResponse response) throws AdminException {
        Long userId=RequestUtil.getLong(request, "userId");
        String realname=RequestUtil.getString(request, "name");
        String password=RequestUtil.getString(request, "password");
        String gameids=RequestUtil.getString(request, "gameids");
        String promptids=RequestUtil.getString(request, "promptids");
        Integer isadmin=RequestUtil.getInteger(request, "isadmin");
        Integer status=RequestUtil.getInteger(request, "status");

        AdminUserTO userTO=new AdminUserTO();
        userTO.setId(userId);
        if(StringUtils.isNotBlank(realname)) {// 判断realname是否为空
            userTO.setRealname(realname);
        }
        if(StringUtils.isNotBlank(password)) {// 判断password是否为空
            try {
                password=MessageDigestUtil.getMD5(password + Constants.PASSWORD_SALT_KEY);
            } catch(Exception e) {
                e.printStackTrace();
            }
            userTO.setPassword(password);
        }
        userTO.setIsadmin(YesNoType.getById(isadmin));
        userTO.setStatusType(StatusType.getById(status));
        userTO.setGameids(gameids);
        userTO.setPromptids(promptids);
        adminUserService.updateAdminUser(userTO);
        return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getOkStatusMsg(null));
    }

}
