package com.gamephone.acs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gamephone.acs.model.UserGames;


public interface UserGamesMapper {

    public int createUserGames(UserGames userGame);
    
    public void updateUserGameById(UserGames userGame);
    
    public UserGames getUserGamesById(@Param(value="id") Integer id);

    public UserGames getUserGamesByImei(@Param(value="imei") String imei);
    
    public List<UserGames> getGamesByUserId(@Param(value="userId")  Integer userId);
    
    public UserGames getUserLoginGame(@Param(value="userId")  Integer userId, @Param(value="gameId")  Integer gameId, @Param(value="serverId")  Integer serverId);
}
