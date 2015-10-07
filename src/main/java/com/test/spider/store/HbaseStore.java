package com.test.spider.store;

import java.util.Map;

import com.test.spider.domain.Page;
import com.test.spider.utils.HbaseUtils;
import com.test.spider.utils.RedisUtils;

public class HbaseStore implements Storeable {

	HbaseUtils hbaseUtils = new HbaseUtils();
	RedisUtils redisUtils = new RedisUtils();
	@Override
	public void store(Page page) {
		String goodsid = page.getGoodsid();
		redisUtils.add("solr_index", goodsid);
		Map<String, String> map = page.getMap();
		try{
			hbaseUtils.put(HbaseUtils.TABLE_NAME, goodsid, HbaseUtils.COLUMNFAMILY_1, HbaseUtils.COLUMNFAMILY_1_DATA_URL, page.getUrl());
			hbaseUtils.put(HbaseUtils.TABLE_NAME, goodsid, HbaseUtils.COLUMNFAMILY_1, HbaseUtils.COLUMNFAMILY_1_PIC_URL, map.get("picurl"));
			hbaseUtils.put(HbaseUtils.TABLE_NAME, goodsid, HbaseUtils.COLUMNFAMILY_1, HbaseUtils.COLUMNFAMILY_1_PRICE, map.get("price"));
			hbaseUtils.put(HbaseUtils.TABLE_NAME, goodsid, HbaseUtils.COLUMNFAMILY_1, HbaseUtils.COLUMNFAMILY_1_TITLE, map.get("title"));
			hbaseUtils.put(HbaseUtils.TABLE_NAME, goodsid, HbaseUtils.COLUMNFAMILY_2, HbaseUtils.COLUMNFAMILY_2_PARAM, map.get("spec"));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

}
