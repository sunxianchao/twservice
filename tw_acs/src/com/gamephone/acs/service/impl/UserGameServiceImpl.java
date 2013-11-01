package com.gamephone.acs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gamephone.acs.cache.impl.UserGameCacheRedis;
import com.gamephone.acs.dao.UserGamesMapper;
import com.gamephone.acs.exception.AcsException;
import com.gamephone.acs.model.UserGames;
import com.gamephone.acs.service.UserGameService;

@Service
public class UserGameServiceImpl implements UserGameService{

    @Autowired
    private UserGamesMapper userGamesMapper;
    
    @Autowired
    private UserGameCacheRedis userGameCache;
   
    @Override
    @Transactional
    public int createUserGames(UserGames userGame)  throws AcsException{
         int retVal = userGamesMapper.createUserGames(userGame);
         if(retVal > 0){
             userGameCache.writeUserGames(userGame);
         }
         return retVal;
    }

    @Override
    @Transactional
    public void updateUserGameById(UserGames userGame)  throws AcsException{
        userGamesMapper.updateUserGameById(userGame);
        userGameCache.writeUserGames(userGame);
    }

    @Override
    public List<UserGames> getGamesByUserId(Integer userId)  throws AcsException{
        return userGamesMapper.getGamesByUserId(userId);
    }

    @Override
    public UserGames getCurrentUserGamesByUserId(Integer userId) throws AcsException{
        return userGameCache.getUserGamesByUserId(userId);
    }
    
    @Override
    public UserGames getUserLoginGame(Integer userId, Integer gameId, Integer serverId)  throws AcsException{
        return userGamesMapper.getUserLoginGame(userId, gameId, serverId);
    }

    @Override
    public UserGames getUserGamesById(Integer id) throws AcsException {
        UserGames userGame=userGameCache.getUserGames(id);
        if(userGame == null){
            userGame=userGamesMapper.getUserGamesById(id);
            userGameCache.writeUserGames(userGame);
        }
        return userGame;
    }
 
    public UserGames getUserGamesByImei(String imei) throws AcsException{
        UserGames userGame=userGameCache.getUserGames(imei);
        if(userGame == null){
            userGame=userGamesMapper.getUserGamesByImei(imei);
            userGameCache.writeUserGames(userGame);
        }
        return userGame;
    }
}
