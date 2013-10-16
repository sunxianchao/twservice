package com.gamephone.acs.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gamephone.acs.cache.impl.UserCacheRedis;
import com.gamephone.acs.cache.impl.UserGameCacheRedis;
import com.gamephone.acs.dao.UserMapper;
import com.gamephone.acs.exception.AcsException;
import com.gamephone.acs.model.User;
import com.gamephone.acs.model.UserGames;
import com.gamephone.acs.service.UserGameService;
import com.gamephone.acs.service.UserService;
import com.gamephone.common.cache.OnlineUserCache;
import com.gamephone.common.context.SystemProperties;
import com.gamephone.common.to.OnlineUser;
import com.gamephone.common.util.MessageDigestUtil;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserGameCacheRedis userGameCache;
    
    @Autowired
    private UserCacheRedis userCache;
    
    @Autowired
    private UserGameService userGameService;
    
    @Autowired
    private OnlineUserCache onlineUserCache;
    
    @Override
    @Transactional
    public int createUser(User user, Integer currentUserGameId) throws AcsException{
        userMapper.createUser(user);
        UserGames userGame=userGameCache.getUserGames(currentUserGameId);
        if(userGame.getUserId() == null){// 该设备首次注册
            userGame.setUserId(user.getId());
            userGameService.updateUserGameById(userGame);
        }else{// 该设备不是第一次注册用户
            userGame.setUserId(user.getId());
            userGameService.createUserGames(userGame);
        }
        userCache.writeUser(user);
        return user.getId();
    }

    @Override
    public User getUserByUserName(String userName) throws AcsException{
        User user=userCache.getUserByName(userName);
        if(user == null){
            user = userMapper.getUserByUserName(userName);
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) throws AcsException{
        User user=userCache.getUserByEmail(email);
        if(user == null){
            user = userMapper.getUserByEmail(email);
        }
        return user;
    }
    
    
    @Override
    public User checkLogin(Map<String, String> login) throws AcsException{
        User user=userCache.readLoginUser(login.get("userName"));
        if(user == null){
            user=userMapper.checkLogin(login);
            userCache.writeLoginUser(user.getUserName(), user);
        }else{
            if(!user.getPassword().equals(login.get("password"))){
                throw new AcsException(SystemProperties.getProperty("login.fail.account.or.password.isnot.correct"));
            }
        }
        return user;
    }

    @Override
    public User getThirdUser(Integer userType, String tid) throws AcsException{
        return userMapper.getThirdUser(userType, tid);
    }
    
    @Override
    public OnlineUser login(User user, String ip) throws AcsException{
        UserGames usergame=userGameCache.getUserGamesByUserId(user.getId());
        if(usergame != null){
            String token=MessageDigestUtil.getMD5(String.format("%s%s%s%s", user.getId(), StringUtils.isEmpty(user.getPassword())?"":user.getPassword(), usergame.getGameId(), System.currentTimeMillis()));
            Date currentTime=new Date();
            OnlineUser onlineUser = new OnlineUser();
            onlineUser.setToken(token);
            onlineUser.setUserId(user.getId());
            onlineUser.setGameId(usergame.getGameId());
            onlineUser.setLoginDate(currentTime);
            onlineUser.setLastActDate(currentTime);
            onlineUser.setUserGameId(usergame.getId());
            try {
                onlineUserCache.writeOnlineUser(onlineUser);
            } catch(TimeoutException e) {
                e.printStackTrace();
                throw new AcsException(e);
            }
            usergame.setLastIP(ip);
            usergame.setUserId(user.getId());
//            usergame.setLastLoginDate(currentTime);
            userGameService.updateUserGameById(usergame);
            return onlineUser;
        }else{
            return null;
        }
    }

    @Override
    public User findPasword(String userName, String email) throws AcsException {
        return userMapper.findPasword(userName, email);
    }

    @Override
    public int updateUserPassword(Integer id, String userName, String newPassword) throws AcsException {
        int ret=userMapper.updateUserPassword(newPassword, id);
        if(ret>0){
            userCache.removeLoginUser(userName);
        }
        return ret;
    }

}
