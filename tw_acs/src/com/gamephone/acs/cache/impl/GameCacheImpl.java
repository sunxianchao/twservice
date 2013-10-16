package com.gamephone.acs.cache.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.gamephone.acs.AcsCacheConstant;
import com.gamephone.acs.cache.GameCache;
import com.gamephone.acs.dao.GameMapper;
import com.gamephone.acs.exception.AcsException;
import com.gamephone.common.cache.CommonAcsCache;
import com.gamephone.common.to.GameTO;
import com.gamephone.common.util.JsonUtil;

public class GameCacheImpl extends CommonAcsCache implements GameCache {

    private GameMapper gameMapper;
    
    public void setGameMapper(GameMapper gameMapper) {
        this.gameMapper=gameMapper;
    }

    @Override
    public List<GameTO> getAllGames() throws AcsException {
        return gameMapper.getAllGames();
    }

    @Override
    public GameTO getGameById(Integer id) throws AcsException {
        String key=AcsCacheConstant.PREFIX_REDIS_GAME_KEY;
        GameTO game=null;
        try {
            String json=getRedisService().hget(key, key+id);
            if(StringUtils.isEmpty(json)){
               game=gameMapper.getGameTOById(id);
               getRedisService().hset(key, key+id, JsonUtil.Object2Json(game));
            }else{
                game=JsonUtil.Json2Object(json, GameTO.class);
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new AcsException(e);
        }
        return game;
    }

}
