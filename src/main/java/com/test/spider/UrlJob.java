package com.test.spider;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.test.spider.utils.RedisUtils;

public class UrlJob implements Job {

	RedisUtils redisUtils = new RedisUtils();
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		List<String> list = redisUtils.lrange(RedisUtils.start_url, 0, -1);
		for (String url : list) {
			redisUtils.add(RedisUtils.heightkey, url);
		}
		
	}

}
