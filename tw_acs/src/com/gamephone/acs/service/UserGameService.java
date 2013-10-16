package com.gamephone.acs.service;

import java.util.List;

import com.gamephone.acs.exception.AcsException;
import com.gamephone.acs.model.UserGames;
import com.gamephone.common.to.GameTO;


public interface UserGameService {

    public int createUserGames(UserGames userGame) throws AcsException;
    
    public void updateUserGameById(UserGames userGame) throws AcsException;
    
    public UserGames getUserGamesByImei(String imei) throws AcsException;
    
    public UserGames getUserGamesById(Integer id) throws AcsException;
    
    public List<UserGames> getGamesByUserId(Integer userId) throws AcsException;
    
    public UserGames getUserLoginGame(Integer userId, Integer gameId, Integer serverId) throws AcsException;
    
}
