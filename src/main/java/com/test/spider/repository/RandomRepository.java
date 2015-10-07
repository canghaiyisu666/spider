package com.test.spider.repository;

import java.util.HashMap;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.test.spider.utils.DomainUtils;

public class RandomRepository implements Repository {
	HashMap<String, Queue<String>> hashMap = new HashMap<String, Queue<String>>();
	//HashMap<String(网站顶级域名), String(网站在redis中对于的一个list列表)> hashMap = new HashMap<String, String>();
	Random random = new Random();
	@Override
	public String poll() {
		String[] keys = hashMap.keySet().toArray(new String[0]);
		int nextInt = random.nextInt(keys.length);
		Queue<String> queue = hashMap.get(keys[nextInt]);
		return queue.poll();
	}

	@Override
	public void add(String nexturl) {
		String topdomain = DomainUtils.getTopDomain(nexturl);
		Queue<String> queue = hashMap.get(topdomain);
		if(queue==null){
			queue = new ConcurrentLinkedQueue<String>();
			hashMap.put(topdomain, queue);
		}
		queue.add(nexturl);
	}

	@Override
	public void addHigh(String nexturl) {
		add(nexturl);
	}
	
	
	
	
}
