package com.test.spider;

import static org.junit.Assert.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpProxyTest {
	
	/**
	 * 使用代理IP抓取页面
	 * Httpclient 4.x
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		HttpClientBuilder builder = HttpClients.custom();
		// TODO 下面的代理IP和端口就需要从代理Ip库中读取
		String ip = "202.194.101.150";
		int port = 80;
		HttpHost proxy = new HttpHost(ip, port);
		CloseableHttpClient client = builder.setProxy(proxy ).build();
		String url = "http://item.jd.com/1514794.html";
		HttpGet request = new HttpGet(url);
		try {
			CloseableHttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			System.out.println(EntityUtils.toString(entity));
		} catch (HttpHostConnectException e) {
			System.out.println("代理异常，代理IP为："+ip+"，代理端口为："+port);
		}
		
	}

}
