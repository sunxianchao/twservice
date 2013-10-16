package com.gamephone.common.cache;

import com.gamephone.common.redis.RedisService;


public class CommonAcsCache {
    
    private int expiredTime = 60*60*2;
    
	private RedisService redisService;

	public RedisService getRedisService() {
		return redisService;
	}

	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}

	public void setExpiredTime(int expiredTime) {
        this.expiredTime = expiredTime;
    }
	
	public int getExpiredTime(){
	    return this.expiredTime;
	}
}
