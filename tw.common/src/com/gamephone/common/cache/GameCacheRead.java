package com.gamephone.common.cache;

import org.apache.commons.lang.StringUtils;

import com.gamephone.common.to.GameTO;
import com.gamephone.common.util.JsonUtil;


public class GameCacheRead extends CommonAcsCache{

    public static final String PREFIX_REDIS_GAME_KEY = "game:id:";
    
    public static final String PREFIX_REDIS_USER_GAME_USERID_KEY = "acs:user.game:userid:";

    public GameTO getGameById(Integer id) {
        String key=PREFIX_REDIS_GAME_KEY;
        GameTO game=null;
        try {
            String json=getRedisService().hget(key, key+id);
            if(!StringUtils.isEmpty(json)){
                game=JsonUtil.Json2Object(json, GameTO.class);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return game;
    }
    
}
