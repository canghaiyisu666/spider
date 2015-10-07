package com.test.spider.repository;

public interface Repository {

	String poll();

	void add(String nexturl);

	void addHigh(String nexturl);

}
