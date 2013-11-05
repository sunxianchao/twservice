package com.gamephone.acs.web;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gamephone.acs.exception.AcsException;
import com.gamephone.acs.model.User;
import com.gamephone.acs.service.UserService;
import com.gamephone.acs.cache.GameCache;
import com.gamephone.common.Constants;
import com.gamephone.common.cache.OnlineUserCache;
import com.gamephone.common.context.SystemProperties;
import com.gamephone.common.to.GameTO;
import com.gamephone.common.to.OnlineUser;
import com.gamephone.common.to.Result;
import com.gamephone.common.util.AccountUtil;
import com.gamephone.common.util.DES;
import com.gamephone.common.util.EmailUtil;
import com.gamephone.common.util.MessageDigestUtil;
import com.gamephone.common.util.RadomUtil;
import com.gamephone.common.util.RequestUtil;

@Controller
public class UserController implements Constants{

    private static final Logger logger=Logger.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private GameCache gameCache;
    
    @Autowired
    private OnlineUserCache onlineUserCache;
    
    @RequestMapping("/service/user/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws AcsException{
        String account=RequestUtil.getString(request, "account");
        String password=RequestUtil.getString(request, "password");
        String ip=RequestUtil.getString(request, "ip");
        Integer gameId=RequestUtil.getInteger(request, "gameId");
        GameTO game=gameCache.getGameById(gameId);
        if(game == null){
            throw new AcsException(SystemProperties.getProperty("game.is.not.exist"));
        }
        if(ip == null) {
            ip=RequestUtil.getUserIpAddr(request);
        }
        if(StringUtils.isBlank(password)){
            throw new AcsException(SystemProperties.getProperty("password.is.illegal"));
        }
        if(StringUtils.isBlank(account) || !AccountUtil.isAvailableUsername(account)){
            throw new AcsException(SystemProperties.getProperty("account.is.illegal"));
        }
        try {
            password=DES.decode(password, game.getSecretKey());
        } catch(Exception e) {
            e.printStackTrace();
            throw new AcsException(e);
        }
        if(!AccountUtil.isAvailablePassword(password)){
            throw new AcsException(SystemProperties.getProperty("password.is.illegal"));
        }
        String passwordSalt=SystemProperties.getProperty("phonegame.pwd.salt")+password.toLowerCase().trim();
        Map<String, String> loginMap=new HashMap<String, String>();
        loginMap.put("userName", account);
        loginMap.put("password", MessageDigestUtil.getMD5(passwordSalt));
        User user=null;
        try {
            user=userService.checkLogin(loginMap);
        } catch(Exception e) {
            throw new AcsException(SystemProperties.getProperty("login.fail.account.or.password.isnot.correct"));
        }
        if(user == null){
            throw new AcsException(SystemProperties.getProperty("login.fail.account.or.password.isnot.correct"));
        }
        OnlineUser onlineUser=userService.login(user, ip);
        Result<OnlineUser> result=new Result<OnlineUser>();
        if(onlineUser != null){
            request.setAttribute("onlineUser", onlineUser);
            result.setBusinessResult(onlineUser);
            result.setSuccess(true);
        }else{
            result.setSuccess(false);
            result.setErrorMsg(SystemProperties.getProperty("login.fail.exit.game.retry"));
        }
        return new ModelAndView(JSON_VIEW, JSON_ROOT, result);
    }
    
    @RequestMapping("/service/user/register")
    public ModelAndView register(HttpServletRequest request, HttpServletResponse response) throws AcsException{
        String account=RequestUtil.getString(request, "account");
        String password=RequestUtil.getString(request, "password");
        String rePassword=RequestUtil.getString(request, "repassword");
        String email=RequestUtil.getString(request, "email");
        String ip=RequestUtil.getString(request, "ip");
        Integer gameId=RequestUtil.getInteger(request, "gameId");
        String tid=RequestUtil.getString(request, "tid");
        Integer currentUserGameId=RequestUtil.getInteger(request, "currentUserGameId");
        Integer promptChannelId=RequestUtil.getInteger(request, PROMPT_CHANNEL_ID);
        if(ip == null) {
            ip=RequestUtil.getUserIpAddr(request);
        }
        if(StringUtils.isBlank(password)){
            throw new AcsException(SystemProperties.getProperty("password.is.illegal"));
        }
        if((StringUtils.isBlank(account) || !AccountUtil.isAvailableUsername(account)) && (StringUtils.isBlank(tid))){
            throw new AcsException(SystemProperties.getProperty("account.is.illegal"));
        }
        
        if(!password.equals(rePassword)){
            throw new AcsException(SystemProperties.getProperty("repassword.inconformity"));
        }
        GameTO game=gameCache.getGameById(gameId);
        if(game == null){
            logger.error(SystemProperties.getProperty("game.is.not.exit"));
            throw new AcsException(SystemProperties.getProperty("game.is.not.exist"));
        }
        try {
            password=DES.decode(password, game.getSecretKey());
        } catch(Exception e) {
            e.printStackTrace();
            throw new AcsException(e);
        }
        if(!AccountUtil.isAvailablePassword(password)){
            throw new AcsException(SystemProperties.getProperty("password.is.illegal"));
        }
        User user=userService.getUserByUserName(account);
        if(user != null){
            throw new AcsException(SystemProperties.getProperty("account.username.exist"));
        }
        user=userService.getUserByEmail(email);
        if(user != null){
            throw new AcsException(SystemProperties.getProperty("account.email.exist"));
        }
        user=new User();
        String passwordSalt=SystemProperties.getProperty("phonegame.pwd.salt")+password.toLowerCase().trim();
        user.setUserName(account);
        user.setTid("0");
        user.setEmail(email);
        user.setChannelId(promptChannelId);
        user.setRegisterGameId(gameId);
        user.setPassword(MessageDigestUtil.getMD5(passwordSalt));
        userService.createUser(user, currentUserGameId);
        return login(request, response);
    }
    
    @RequestMapping("/service/user/otherlogin")
    public ModelAndView otherLogin(HttpServletRequest request, HttpServletResponse response) throws AcsException{
        String tid=RequestUtil.getString(request, "tid");
        Integer userType=RequestUtil.getInteger(request, "userType");
        String ip=RequestUtil.getString(request, "ip");
        Integer gameId=RequestUtil.getInteger(request, "gameId");
        Integer currentUserGameId=RequestUtil.getInteger(request, "currentUserGameId");
        Integer promptChannelId=RequestUtil.getInteger(request, PROMPT_CHANNEL_ID);
        if(ip == null) {
            ip=RequestUtil.getUserIpAddr(request);
        }
        if(StringUtils.isBlank(tid) || userType == null){
            throw new AcsException(SystemProperties.getProperty("account.is.illegal"));
        }
        GameTO game=gameCache.getGameById(gameId);
        if(game == null){
            logger.error(SystemProperties.getProperty("game.is.not.exit"));
            throw new AcsException(SystemProperties.getProperty("game.is.not.exist"));
        }
        
        User user=userService.getThirdUser(userType, tid);
        if(user == null){
            user=new User();
            String passwordSalt=SystemProperties.getProperty("phonegame.pwd.salt")+"".toLowerCase().trim();
            user.setUserName("");
            user.setPassword(MessageDigestUtil.getMD5(passwordSalt));
            user.setTid(tid);
            user.setUserType(userType);
            user.setChannelId(promptChannelId);
            user.setRegisterGameId(gameId);
            userService.createUser(user, currentUserGameId);
        }
        
        OnlineUser onlineUser=userService.login(user, ip);
        Result<OnlineUser> result=new Result<OnlineUser>();
        if(onlineUser != null){
            onlineUser.setTid(tid);
            request.setAttribute("onlineUser", onlineUser);
            result.setBusinessResult(onlineUser);
            result.setSuccess(true);
        }else{
            result.setSuccess(false);
            result.setErrorMsg(SystemProperties.getProperty("login.fail.exit.game.retry"));
        }
        return new ModelAndView(JSON_VIEW, JSON_ROOT, result);
    }
    
    @RequestMapping("/common/user/changepwd")
    public ModelAndView changePassword(HttpServletRequest request, HttpServletResponse response) throws AcsException{
        String account=RequestUtil.getString(request, "account");
        String oldpassword=RequestUtil.getString(request, "oldpassword");
        String newpassword=RequestUtil.getString(request, "newpassword");
        String repassword=RequestUtil.getString(request, "repassword");
        Integer gameId=RequestUtil.getInteger(request, "gameId");
        if((StringUtils.isBlank(account) || !AccountUtil.isAvailableUsername(account))){
            throw new AcsException(SystemProperties.getProperty("account.is.illegal"));
        }
        if(StringUtils.isBlank(oldpassword)){
            throw new AcsException(SystemProperties.getProperty("password.is.illegal"));
        }
        if(!newpassword.equals(repassword)){
            throw new AcsException(SystemProperties.getProperty("repassword.inconformity"));
        }
        User user=userService.getUserByUserName(account);
        if(user == null){
            throw new AcsException(SystemProperties.getProperty("account.username.exist"));
        }
        GameTO game=gameCache.getGameById(gameId);
        if(game == null){
            logger.error(SystemProperties.getProperty("game.is.not.exit"));
            throw new AcsException(SystemProperties.getProperty("game.is.not.exist"));
        }
        try {
            oldpassword=DES.decode(oldpassword, game.getSecretKey());
            newpassword=DES.decode(newpassword, game.getSecretKey());
        } catch(Exception e) {
            e.printStackTrace();
            throw new AcsException(SystemProperties.getProperty("unknow.exception"));
        }
        String passwordSalt=SystemProperties.getProperty("phonegame.pwd.salt")+oldpassword.toLowerCase().trim();
        Map<String, String> loginMap=new HashMap<String, String>();
        loginMap.put("userName", account);
        loginMap.put("password", MessageDigestUtil.getMD5(passwordSalt));
        user=userService.checkLogin(loginMap);
        if(user == null){
            throw new AcsException(SystemProperties.getProperty("login.fail.account.or.password.isnot.correct"));
        }
        newpassword=SystemProperties.getProperty("phonegame.pwd.salt")+newpassword;
        int ret=userService.updateUserPassword(user.getId(), user.getUserName(), MessageDigestUtil.getMD5(newpassword));
        Result<String> result=new Result<String>();
        String message=SystemProperties.getProperty("unknow.exception");
        if(ret>0){
            message=SystemProperties.getProperty("change.password.success");
            result.setSuccess(true);
        }
        result.setBusinessResult(message);
        return new ModelAndView(JSON_VIEW, JSON_ROOT, result);
    }
    
    @RequestMapping("/common/user/findpwd")
    public ModelAndView findPassword(HttpServletRequest request, HttpServletResponse response) throws AcsException{
        String account=RequestUtil.getString(request, "account");
        String email=RequestUtil.getString(request, "email");
        if((StringUtils.isBlank(account) || !AccountUtil.isAvailableUsername(account))){
            throw new AcsException(SystemProperties.getProperty("account.is.illegal"));
        }
        if((StringUtils.isBlank(email) || !AccountUtil.isEmail(email))){
            throw new AcsException(SystemProperties.getProperty("email.is.illegal"));
        }
        Result<String> result=new Result<String>();
        User user=userService.findPasword(account, email);
        if(user != null){
            String title=SystemProperties.getProperty("findpwd.email.title");
            String content=SystemProperties.getProperty("findpwd.email.content");
            String newpwd=RadomUtil.getPass(6);
            content=content.replace("#", newpwd);
            String newpassword=SystemProperties.getProperty("phonegame.pwd.salt")+newpwd.toLowerCase().trim();
            int ret=userService.updateUserPassword(user.getId(), user.getUserName(), MessageDigestUtil.getMD5(newpassword));
            if(ret>0){
                String message=SystemProperties.getProperty("change.password.success");
                result.setBusinessResult(message);
                result.setSuccess(true);
                EmailUtil.sendMail(email, title, content, false);
            }else{
                throw new AcsException(SystemProperties.getProperty("unknow.exception"));
            }
        }else{
            throw new AcsException(SystemProperties.getProperty("findpwd.not.exist.account"));
        }
        return new ModelAndView(JSON_VIEW, JSON_ROOT, result);
    }
    
    @RequestMapping("/service/user/heartbeat")
    public ModelAndView heartBeat(HttpServletRequest request, HttpServletResponse response) throws AcsException{
        OnlineUser onlineUser=(OnlineUser)request.getAttribute(Constants.ONLINE_USER);
        Result<OnlineUser> result=new Result<OnlineUser>();
        try {
            onlineUserCache.writeOnlineUser(onlineUser);
            request.setAttribute("onlineUser", onlineUser);
            result.setBusinessResult(onlineUser);
            result.setSuccess(true);
        } catch(Exception e) {
            throw new AcsException(SystemProperties.getProperty("login.timeout"));
        }
        return new ModelAndView(JSON_VIEW, JSON_ROOT, result);
    }

    @RequestMapping("/service/user/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response)throws AcsException{
        OnlineUser onlineUser=(OnlineUser)request.getAttribute(Constants.ONLINE_USER);
        try {
            onlineUserCache.removeOnlineUser(onlineUser);
        } catch(TimeoutException e) {
            e.printStackTrace();
        }
    }
}
