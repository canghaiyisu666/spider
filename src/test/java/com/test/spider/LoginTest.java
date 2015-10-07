package com.test.spider;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class LoginTest {
	
	/**
	 * 模拟登录
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		HttpClientBuilder builder = HttpClients.custom();
		CloseableHttpClient client = builder.build();
		
		HttpPost httpPost = new HttpPost("http://svn.jundie.net/user/login");
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("uid", "crxy"));
		parameters.add(new BasicNameValuePair("pwd", "www.crxy.cn"));
		HttpEntity entity = new UrlEncodedFormEntity(parameters );
		httpPost.setEntity(entity);
		
		CloseableHttpResponse response = client.execute(httpPost);
		
		int statusCode = response.getStatusLine().getStatusCode();
		if(statusCode==302){
			Header[] headers = response.getHeaders("location");
			String redirectUrl = "";
			if(headers.length>0){
				redirectUrl = headers[0].getValue();
			}
			
			httpPost.setURI(new URI("http://svn.jundie.net"+redirectUrl));
			response = client.execute(httpPost);
			System.out.println(EntityUtils.toString(response.getEntity()));
			
		}
		
		
		
		
		
		
	}

}
