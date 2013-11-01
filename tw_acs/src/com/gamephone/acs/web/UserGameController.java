package com.gamephone.acs.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gamephone.acs.cache.GameCache;
import com.gamephone.acs.exception.AcsException;
import com.gamephone.acs.model.GameRole;
import com.gamephone.acs.model.ServerLog;
import com.gamephone.acs.model.UserGames;
import com.gamephone.acs.service.ServerService;
import com.gamephone.acs.service.UserGameService;
import com.gamephone.common.Constants;
import com.gamephone.common.context.GlobeContext;
import com.gamephone.common.context.SystemProperties;
import com.gamephone.common.to.GameTO;
import com.gamephone.common.to.OnlineUser;
import com.gamephone.common.to.Result;
import com.gamephone.common.util.RequestUtil;

@Controller
public class UserGameController implements Constants{

    private static final Logger logger=Logger.getLogger(UserGameController.class);
    
    @Autowired
    private UserGameService userGameService;
    
    private GameCache gameCache;
    
    @Autowired
    private ServerService serverService;
    
    @RequestMapping("/service/game/init")
    public ModelAndView gameInit(UserGames userGame, HttpServletRequest request, HttpServletResponse response) throws AcsException{
        Integer id=RequestUtil.getInteger(request, "id");
        Integer gameId=RequestUtil.getInteger(request, "gameId");
        String macAddress=RequestUtil.getString(request, "macAddress");
        String imei=RequestUtil.getString(request, "imei");
        String ua=RequestUtil.getString(request, "ua");
        String lastIp=RequestUtil.getUserIpAddr(request);
        gameCache=(GameCache)GlobeContext.getApplicationContext().getBean("gameCache");
        GameTO game=gameCache.getGameById(gameId);
        if(game == null){
            throw new AcsException(SystemProperties.getProperty("game.is.not.exist"));
        }
        userGame.setId(id);
        userGame.setGameId(gameId);
        userGame.setLastIP(lastIp);
        userGame.setImei(imei);
        userGame.setLastUA(ua);
        userGame.setMacAddress(macAddress);
        
        UserGames tmpUserGame=userGameService.getUserGamesByImei(imei);
        if(tmpUserGame == null){
            userGameService.createUserGames(userGame);
        }else{
            userGame=tmpUserGame;
        }
        
        Result<UserGames> result=new Result<UserGames>();
        result.setBusinessResult(userGame);
        result.setSuccess(true);
        return new ModelAndView(JSON_VIEW, JSON_ROOT, result);
    }
    
    @RequestMapping("/service/game/loginserver")
    public void loginServer(HttpServletRequest request, HttpServletResponse response) throws AcsException{
        Integer roleId=RequestUtil.getInteger(request, "roleId");
        String roleName=RequestUtil.getString(request, "roleName");
        Integer serverId=RequestUtil.getInteger(request, "serverId");
        String serverName=RequestUtil.getString(request, "serverName");
        Integer promptChannelId=RequestUtil.getInteger(request, PROMPT_CHANNEL_ID);
        OnlineUser onlineUser=(OnlineUser)request.getAttribute(Constants.ONLINE_USER);
        if(onlineUser == null){
            throw new AcsException(SystemProperties.getProperty("account.exception"));
        }
        gameCache=(GameCache)GlobeContext.getApplicationContext().getBean("gameCache");
        GameTO game=gameCache.getGameById(onlineUser.getGameId());
        if(game == null){
            throw new AcsException(SystemProperties.getProperty("game.is.not.exist"));
        }
        
        UserGames userGame=userGameService.getUserGamesById(onlineUser.getUserGameId());
        
        ServerLog log=new ServerLog();
        log.setChannelId(promptChannelId);
        
        GameRole role=new GameRole();
        role.setGameId(game.getId());
        role.setUserId(onlineUser.getUserId());
        role.setServerId(serverId);
        role.setServerName(serverName);
        role.setRoleId(roleId);
        role.setRoleName(roleName);
        onlineUser.setServerId(serverId);
        serverService.loginServer(role, log, userGame, onlineUser);
    }
    
    @RequestMapping("/remote/game/userpaymentinfo")
    public void updateUserPaymentInfo(HttpServletRequest request, HttpServletResponse response) throws AcsException{
        Integer amount=RequestUtil.getInteger(request, "amount");
        Integer userId=RequestUtil.getInteger(request, "userId");
        UserGames userGame=userGameService.getCurrentUserGamesByUserId(userId);
        if(userGame != null){
            userGame.setAmount(amount);
            logger.info("userid:"+userId+"userGame="+userGame.getId());
            userGameService.updateUserGameById(userGame);
        }
    }
}
