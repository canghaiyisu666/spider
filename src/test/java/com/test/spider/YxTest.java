package com.test.spider;

import static org.junit.Assert.*;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.junit.Test;

import com.test.spider.utils.HtmlUtils;
import com.test.spider.utils.PageUtils;

public class YxTest {
	
	
	@Test
	public void test() throws Exception {
		String content = PageUtils.getContent("http://www.yixun.com/category.html?YTAG=1.100090000");
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode rootNode = htmlCleaner.clean(content);
		String value = HtmlUtils.getAttributeByName(rootNode, "href", "//*[@id=\"category\"]/div[2]/div[2]/div[2]/div[1]/div[1]/div[2]/a[1]");
		System.out.println(value);
		
	}

}
