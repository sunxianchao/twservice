package com.gamephone.acs.cache;

import com.gamephone.acs.exception.AcsException;
import com.gamephone.acs.model.GameRole;
import com.gamephone.acs.model.UserGames;


public interface UserGameCache {

    public void writeUserGames(UserGames userGame)  throws AcsException;
    
    public UserGames getUserGames(String imei) throws AcsException;
    
    public UserGames getUserGames(Integer id) throws AcsException;
    
    public UserGames getUserGamesByUserId(Integer userId) throws AcsException;
    
    public void writeGameRole(GameRole role) throws AcsException;
    
    public GameRole getGameRole(GameRole role) throws AcsException;
}
