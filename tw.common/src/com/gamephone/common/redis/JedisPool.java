package com.gamephone.common.redis;

import org.apache.commons.pool.impl.GenericObjectPool.Config;

public class JedisPool extends Config {
	public JedisPool() {
		super();
		super.testOnBorrow = true;
		super.testOnReturn = true;
		super.testWhileIdle = true;
	}
	public void setMaxIdle(int maxIdle){
		super.maxIdle=maxIdle;
	}
	public void setMaxActive(int maxActive){
		super.maxActive=maxActive;
	}
	public void setMaxWait(int maxWait){
		super.maxWait=maxWait;
	}
}
