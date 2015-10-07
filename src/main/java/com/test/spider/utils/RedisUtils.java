package com.test.spider.utils;


import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {
	public static String start_url = "start_url";
	
	public static String heightkey = "spider.todo.height";
	public static String lowkey = "spider.todo.low";
	
	
	JedisPool jedisPool = null;
	public RedisUtils(){
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(10);
		poolConfig.setMaxTotal(100);
		poolConfig.setMaxWaitMillis(10000);
		poolConfig.setTestOnBorrow(true);
		jedisPool = new JedisPool(poolConfig, "192.168.1.170", 6379);
	}
	
	public List<String> lrange(String key,int start,int end){
		Jedis resource = jedisPool.getResource();
		
		List<String> list = resource.lrange(key, start, end);
		jedisPool.returnResourceObject(resource);
		return list;
		
	}
	
	public void add(String lowKey, String url) {
		Jedis resource = jedisPool.getResource();
		resource.lpush(lowKey, url);
		jedisPool.returnResourceObject(resource);
	}
	public String poll(String key) {
		Jedis resource = jedisPool.getResource();
		String result = resource.rpop(key);
		jedisPool.returnResourceObject(resource);
		return result;
	}
	
}
