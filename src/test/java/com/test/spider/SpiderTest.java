package com.test.spider;

import java.util.List;

import org.junit.Test;

import com.test.spider.domain.Page;
import com.test.spider.download.HttpClientDownload;
import com.test.spider.process.JdProcess;
import com.test.spider.store.ConsoleStore;
import com.test.spider.store.HbaseStore;

public class SpiderTest {

	@Test
	public void test() throws Exception {
		Spider spider = new Spider();
		// spider.start();
		spider.setDownloadable(new HttpClientDownload());
		spider.setProcessable(new JdProcess());
		spider.setStoreable(new ConsoleStore());// 测试方法，使用控制台输出结果

		// String url = "http://list.jd.com/list.html?cat=9987,653,655";
		String url = "http://item.jd.com/1856581.html";
		Page page = spider.download(url);
		spider.process(page);
		List<String> urlList = page.getUrlList();
		System.out.println(urlList.size());
		spider.store(page);

	}

}
