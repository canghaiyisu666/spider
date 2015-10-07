package com.test.spider.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.test.spider.utils.Config;

public class FixedThreadPool implements ThreadPool {
	ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(Config.nThread);
	@Override
	public void execute(Runnable runnable) {
		newFixedThreadPool.execute(runnable);
	}

}
