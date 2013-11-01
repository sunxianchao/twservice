package com.gamephone.acs.service.impl;

import java.util.Date;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gamephone.acs.cache.impl.UserGameCacheRedis;
import com.gamephone.acs.dao.GameRoleMapper;
import com.gamephone.acs.dao.ServerLogMapper;
import com.gamephone.acs.exception.AcsException;
import com.gamephone.acs.model.GameRole;
import com.gamephone.acs.model.ServerLog;
import com.gamephone.acs.model.UserGames;
import com.gamephone.acs.service.ServerService;
import com.gamephone.acs.service.UserGameService;
import com.gamephone.common.cache.OnlineUserCache;
import com.gamephone.common.to.OnlineUser;
import com.gamephone.common.util.ThreadPoolUtil;

@Service
public class ServerServiceImpl implements ServerService {

    @Autowired
    private ServerLogMapper serverLogMappper;
    
    @Autowired
    private GameRoleMapper gameRoleMapper;

    @Autowired
    private UserGameCacheRedis userGameCache;
    
    @Autowired
    private UserGameService userGameService;
    
    @Autowired
    private OnlineUserCache onlineUserCache;
    
    private static final Logger logger=Logger.getLogger(ServerServiceImpl.class);

    
    @Override
    @Transactional
    public int createServerLog(ServerLog log) throws AcsException {
        return serverLogMappper.createServerLog(log);
    }

    @Override
    @Transactional
    public GameRole createGameRole(GameRole role) throws AcsException {
        int ret=gameRoleMapper.createGameRole(role);
        if(ret >0){
            userGameCache.writeGameRole(role);
            logger.info("create game role and set to reids");
            return role;
        }
        return null;
    }

    @Override
    public GameRole getGameRole(GameRole role) throws AcsException {
        GameRole r=userGameCache.getGameRole(role);
        if(r == null){
            r=gameRoleMapper.getGameRole(role);
        }
        return r;
    }
    
    @Override
    @Transactional
    public void loginServer(final GameRole role, final ServerLog log, final UserGames userGame, OnlineUser onlineUser) throws AcsException{
        GameRole gr=getGameRole(role);
        if(gr == null){
            gr=createGameRole(role);
        }
        log.setGameRoleId(gr.getId());
        userGame.setGameRoleId(gr.getId());
        userGame.setLastLoginDate(new Date());
        userGame.setUserId(onlineUser.getUserId());
        try {
            userGameService.updateUserGameById(userGame);
            onlineUserCache.writeOnlineUser(onlineUser);
            ThreadPoolUtil.exexuteThread(new ServerLogRunnable(log));
        } catch(TimeoutException e) {
            e.printStackTrace();
        }
    }
    
    class ServerLogRunnable implements Runnable{
        
        private ServerLog log;
        
        public ServerLogRunnable(ServerLog log){
            this.log=log;
        }
        
        @Override
        public void run() {
            try {
                createServerLog(log);
            } catch(AcsException e) {
                e.printStackTrace();
            }
            logger.info("create server log"+log.toString());
        }
    }
}
