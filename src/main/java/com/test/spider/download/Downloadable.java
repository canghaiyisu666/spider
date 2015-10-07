package com.test.spider.download;

import com.test.spider.domain.Page;


public interface Downloadable {
	/**
	 * 下载url
	 * @param url
	 * @return
	 */
	 Page download(String url);
	
}
