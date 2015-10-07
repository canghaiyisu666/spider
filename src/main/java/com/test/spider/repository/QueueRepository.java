package com.test.spider.repository;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 优先级队列
 * @author Administrator
 *
 */
public class QueueRepository implements Repository {
	Queue<String> lowqueue = new ConcurrentLinkedQueue<String>();
	Queue<String> highqueue = new ConcurrentLinkedQueue<String>();
	
	@Override
	public String poll() {
		String url = highqueue.poll();
		if(url==null){
			url = lowqueue.poll();
		}
		return url;
	}

	@Override
	public void add(String nexturl) {
		this.lowqueue.add(nexturl);
	}

	@Override
	public void addHigh(String nexturl) {
		this.highqueue.add(nexturl);
	}
	
	
	
	

}
