package com.test.spider.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageUtils {
	private static Logger logger = LoggerFactory.getLogger(PageUtils.class);
	/**
	 * 获取页面内容
	 * @param url
	 * @return
	 */
	public static String getContent(String url){
		String content = "";
		HttpClientBuilder builder = HttpClients.custom();
		builder.setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36");
		CloseableHttpClient client = builder.build();
		
		HttpGet request = new HttpGet(url);
		try {
			long start_time = System.currentTimeMillis();
			CloseableHttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			content = EntityUtils.toString(entity);
			logger.info("页面下载成功:{},消耗时间:{}",url,System.currentTimeMillis()-start_time);
		} catch (Exception e) {
			logger.error("页面下载失败:{}",url);
			e.printStackTrace();
		}
		return content;
		
	}

}
