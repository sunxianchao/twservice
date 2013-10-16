package com.gamephone.acs.cache.impl;

import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gamephone.acs.AcsCacheConstant;
import com.gamephone.acs.cache.UserGameCache;
import com.gamephone.acs.exception.AcsException;
import com.gamephone.acs.model.GameRole;
import com.gamephone.acs.model.UserGames;
import com.gamephone.common.cache.CommonAcsCache;

public class UserGameCacheRedis extends CommonAcsCache implements UserGameCache {

    private static final Logger logger=Logger.getLogger(UserGameCacheRedis.class);

    @Override
    public void writeUserGames(UserGames userGame) throws AcsException {
        try {
            if(userGame == null)
                return;
            String key=AcsCacheConstant.PREFIX_REDIS_USER_GAME_ID_KEY + userGame.getId();
            String key2=AcsCacheConstant.PREFIX_REDIS_USER_GAME_IMEI_KEY + userGame.getImei();
            if(userGame.getUserId() != null){
                String key3=AcsCacheConstant.PREFIX_REDIS_USER_GAME_USERID_KEY + userGame.getUserId();
                getRedisService().set(key3, userGame);
            }
            getRedisService().set(key, userGame);
            getRedisService().set(key2, userGame);
        } catch(TimeoutException e) {
            e.printStackTrace();
            throw new AcsException(e);
        }
    }

    @Override
    public UserGames getUserGamesByUserId(Integer userId) throws AcsException {
        try {
            if(userId != null && userId.intValue() != 0){
                String key=AcsCacheConstant.PREFIX_REDIS_USER_GAME_USERID_KEY + userId;
                return getRedisService().get(key, UserGames.class);
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new AcsException(e);
        }
        return null;
    }
    
    @Override
    public UserGames getUserGames(Integer id) throws AcsException {
        try {
            if(id != null && id.intValue() != 0){
                String key=AcsCacheConstant.PREFIX_REDIS_USER_GAME_ID_KEY + id;
                return getRedisService().get(key, UserGames.class);
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new AcsException(e);
        }
        return null;
    }
    
    public UserGames getUserGames(String imei) throws AcsException {
        try {
            if(StringUtils.isNotBlank(imei)){
                String key=AcsCacheConstant.PREFIX_REDIS_USER_GAME_IMEI_KEY + imei;
                return getRedisService().get(key, UserGames.class);
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new AcsException(e);
        }
        return null;
    }

    @Override
    public void writeGameRole(GameRole role) throws AcsException {
        try {
            if(role != null) {
                String key=
                    AcsCacheConstant.PERFIX_REDIS_USER_GAME_ROLE_KEY + role.getUserId() + ":" + role.getGameId() + ":"
                        + role.getServerId() + ":" + role.getRoleId();
                getRedisService().set(key, role);
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new AcsException(e);
        }
    }

    @Override
    public GameRole getGameRole(GameRole role) throws AcsException {
        try {
            if(role != null) {
                String key=
                    AcsCacheConstant.PERFIX_REDIS_USER_GAME_ROLE_KEY + role.getUserId() + ":" + role.getGameId() + ":"
                        + role.getServerId() + ":" + role.getRoleId();
                return getRedisService().get(key, GameRole.class);
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new AcsException();
        }
        return null;
    }
}
