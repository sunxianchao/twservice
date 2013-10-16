package com.gamephone.acs.dao;

import com.gamephone.acs.model.GameRole;


public interface GameRoleMapper {

    public int createGameRole(GameRole role);
    
    public GameRole getGameRole(GameRole role);
}
