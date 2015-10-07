package com.test.spider.repository;

import java.util.HashMap;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.test.spider.utils.DomainUtils;
import com.test.spider.utils.RedisUtils;

public class RandomRedisRepository implements Repository {
	HashMap<String,String> hashMap = new HashMap<String, String>();
	Random random = new Random();
	RedisUtils redisUtils = new RedisUtils();
	@Override
	public String poll() {
		String[] keys = hashMap.keySet().toArray(new String[0]);
		int nextInt = random.nextInt(keys.length);
		String redis_key = hashMap.get(keys[nextInt]);
		return redisUtils.poll(redis_key);
	}

	@Override
	public void add(String nexturl) {
		String topdomain = DomainUtils.getTopDomain(nexturl);
		String rediskey = hashMap.get(topdomain);
		if(rediskey==null){
			hashMap.put(topdomain, topdomain);
		}
		redisUtils.add(topdomain, nexturl);
	}

	@Override
	public void addHigh(String nexturl) {
		add(nexturl);
	}
	
	
	
	
}
