package com.gamephone.acs.cache.impl;

import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.StringUtils;

import com.gamephone.acs.AcsCacheConstant;
import com.gamephone.acs.cache.UserCache;
import com.gamephone.acs.exception.AcsException;
import com.gamephone.acs.model.User;
import com.gamephone.common.cache.CommonAcsCache;


public class UserCacheRedis extends CommonAcsCache implements UserCache {

	@Override
	public void writeLoginUser(String userName, User user) throws AcsException {
		try {		
			// 放入redis中并设置过期时间。
		    if(user != null){
		        getRedisService().set(AcsCacheConstant.PREFIX_REDIS_LOGIN_KEY + userName, user);
		    }
		} catch (TimeoutException e) {
			e.printStackTrace();
			throw new AcsException();
		}
	}

	@Override
	public User readLoginUser(String userName) throws AcsException  {
		try {
		    if(StringUtils.isNotBlank(userName)){
		        return getRedisService().get(AcsCacheConstant.PREFIX_REDIS_LOGIN_KEY + userName, User.class);
		    }
		} catch (TimeoutException e) {
			e.printStackTrace();
			throw new AcsException();
		}
		return null;
	}

	@Override
	public Long removeLoginUser(String... userName) throws AcsException {
		try {
			String[] keys = null;
			if(userName != null && userName.length > 0) {
				keys = new String[userName.length];
				
				for(int i = 0; i < keys.length; i++) {
					keys[i] = AcsCacheConstant.PREFIX_REDIS_LOGIN_KEY + userName[i];
				}
			}
			return getRedisService().del(keys);
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return 0L;
	}

    @Override
    public User getUserByName(String userName) throws AcsException {
        try {
            if(StringUtils.isNotEmpty(userName)){
                return getRedisService().get(AcsCacheConstant.PERFIX_REDIS_USER_NAME_KEY+userName, User.class);
            }
        } catch(TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) throws AcsException {
        try {
            if(StringUtils.isNotEmpty(email)){
                return getRedisService().get(AcsCacheConstant.PERFIX_REDIS_USER_EMAIL_KEY+email, User.class);
            }
        } catch(TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void writeUser(User user) throws AcsException {
        try {
            if(user != null){
                getRedisService().set(AcsCacheConstant.PERFIX_REDIS_USER_NAME_KEY+user.getUserName(), user);
                getRedisService().set(AcsCacheConstant.PERFIX_REDIS_USER_EMAIL_KEY+user.getEmail(), user);
            }
        } catch(TimeoutException e) {
            e.printStackTrace();
        }
        
    }
}
