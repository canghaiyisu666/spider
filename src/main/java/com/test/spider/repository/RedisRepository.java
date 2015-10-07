package com.test.spider.repository;

import com.test.spider.utils.RedisUtils;

public class RedisRepository implements Repository {

	RedisUtils redisUtils = new RedisUtils();
	@Override
	public String poll() {
		String url = redisUtils.poll(RedisUtils.heightkey);
		if(url==null){
			url = redisUtils.poll(RedisUtils.lowkey);
		}
		return url;
	}

	@Override
	public void add(String nexturl) {
		redisUtils.add(RedisUtils.lowkey, nexturl);
	}

	@Override
	public void addHigh(String nexturl) {
		redisUtils.add(RedisUtils.heightkey, nexturl);
	}
	
	

}
