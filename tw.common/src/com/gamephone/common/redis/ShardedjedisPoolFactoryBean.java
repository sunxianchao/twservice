package com.gamephone.common.redis;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool.impl.GenericObjectPool.Config;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;

public class ShardedjedisPoolFactoryBean extends AbstractFactoryBean<ShardedJedisPool> {
	
	private Config config; 
	private String shardedInfo;
	private String timeout;


	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public void setShardedInfo(String shardedInfo) {
		this.shardedInfo = shardedInfo;
	}

	
	@Override
	protected ShardedJedisPool createInstance() throws Exception {
			
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		String[] shardStrings = shardedInfo.split(",");
		String[] timeouts = timeout.split(",");
			
		for(int i=0;i<shardStrings.length;i++){
			String[] infos = shardStrings[i].split(":");
			JedisShardInfo jsi = new JedisShardInfo(infos[0], Integer.parseInt(infos[1]), Integer.parseInt(timeouts[i]));
			shards.add(jsi);
		}
			
		ShardedJedisPool shardedJedisPool = new ShardedJedisPool(config, shards, Hashing.MURMUR_HASH);
		return shardedJedisPool;
	}

	@Override
	public Class<?> getObjectType() {
		return ShardedJedisPool.class;
	}

}
