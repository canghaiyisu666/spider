package com.test.spider.utils;

public class SleepUtils {
	
	public static void sleep(long million){
		try {
			Thread.sleep(million);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
