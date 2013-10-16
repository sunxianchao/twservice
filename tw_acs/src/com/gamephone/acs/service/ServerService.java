package com.gamephone.acs.service;

import com.gamephone.acs.exception.AcsException;
import com.gamephone.acs.model.GameRole;
import com.gamephone.acs.model.ServerLog;
import com.gamephone.acs.model.UserGames;
import com.gamephone.common.to.OnlineUser;

public interface ServerService {

    public int createServerLog(ServerLog log) throws AcsException;
    
    public GameRole createGameRole(GameRole role) throws AcsException;
    
    public GameRole getGameRole(GameRole role) throws AcsException;
    
    public void loginServer(GameRole role, ServerLog log, final UserGames ug, OnlineUser onlineUser) throws AcsException;
    
}
