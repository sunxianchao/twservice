package com.gamephone.common.cache;

import java.util.List;
import java.util.concurrent.TimeoutException;

import com.gamephone.common.redis.RedisService;
import com.gamephone.common.redis.ShardedRedisService;
import com.gamephone.common.to.OnlineUser;

public class OnlineUserCache {

    private int expiredTime=60 * 20;

    private final static String PERFIX_ONLINE_USER_KEY="online:user:token:";

    private final static String PERFIX_ONLINE_USER_GAME_KEY="online:user:game:";

    private RedisService onlineUserRedisService;

    public void setOnlineUserRedisService(RedisService onlineUserRedisService) {
        this.onlineUserRedisService=onlineUserRedisService;
    }

    public void writeOnlineUser(OnlineUser onlineUser) throws TimeoutException {
        String key=PERFIX_ONLINE_USER_KEY + onlineUser.getToken();
        onlineUserRedisService.setAndExpire(key, onlineUser, expiredTime);
        if(onlineUser.getServerId() != null && onlineUser.getServerId().intValue() != 0) {
            String gameServerKey=PERFIX_ONLINE_USER_GAME_KEY + onlineUser.getGameId() + ":server:" + onlineUser.getServerId();
            String gameServerTicketKey=gameServerKey +":ticket:"+onlineUser.getToken();
            onlineUserRedisService.hset(gameServerKey, gameServerTicketKey, key);
            onlineUserRedisService.hset(gameServerKey, key, gameServerTicketKey);
        }
        //后台要有一个程序去模糊搜索gameserverkey 找到后循环里面的key值然后到onlineuser中去找如果没找到就删除
    }
    
    public OnlineUser getOnlineUser(String token) throws TimeoutException{
        String key=PERFIX_ONLINE_USER_KEY + token;
        OnlineUser onlineUser=onlineUserRedisService.get(key, OnlineUser.class);
        return onlineUser;
    }

    public void removeOnlineUser(OnlineUser onlineUser) throws TimeoutException{
        String key=PERFIX_ONLINE_USER_KEY + onlineUser.getToken();
        onlineUserRedisService.del(key);
        List<String> keys=onlineUserRedisService.keys(".*"+onlineUser.getToken());
        if(keys != null && !keys.isEmpty()){
            for(String item : keys){
                onlineUserRedisService.del(item);
            }
        }
    }
}
