package com.test.spider.download;

import com.test.spider.domain.Page;
import com.test.spider.utils.PageUtils;

public class HttpClientDownload implements Downloadable {

	@Override
	public Page download(String url) {
		Page page = new Page();
		String content = PageUtils.getContent(url);
		page.setContent(content);
		page.setUrl(url);
		return page;
	}

	

}
