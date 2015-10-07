package com.test.spider.store;

import com.test.spider.domain.Page;

public class ConsoleStore implements Storeable {

	@Override
	public void store(Page page) {
		System.out.println(page.getUrl()+"---"+page.getMap().get("price"));
	}

}
