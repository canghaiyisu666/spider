package com.test.spider.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page {
	
	/**
	 * 临时存储下一页及当前页商品的url
	 */
	private List<String> urlList = new ArrayList<String>();
	
	
	/**
	 * 商品ID
	 */
	private String goodsid;
	
	/**
	 * 存储页面的基本信息
	 */
	private Map<String, String> map = new HashMap<String, String>();
	
	/**
	 * 原始url
	 */
	private String url;
	
	/**
	 * 原始页面内容
	 */
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void addField(String key ,String value){
		this.map.put(key, value);
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public List<String> getUrlList() {
		return urlList;
	}
	
	public void addUrl(String url){
		this.urlList.add(url);
	}
	
	
}
