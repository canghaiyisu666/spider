package com.test.spider.utils;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class HtmlUtils {
	
	/**
	 * 获取指定标签的值
	 * @param rootNode
	 * @param xpath
	 * @return
	 */
	public static String getText(TagNode rootNode,String xpath){
		String value = "";
		Object[] evaluateXPath;
		try {
			evaluateXPath = rootNode.evaluateXPath(xpath);
			if(evaluateXPath.length>0){
				TagNode tagNode = (TagNode)evaluateXPath[0];
				value = tagNode.getText().toString();
			}
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * 获取指定标签指定属性的值
	 * @param rootNode
	 * @param attr
	 * @param xpath
	 * @return
	 */
	public static String getAttributeByName(TagNode rootNode,String attr,String xpath){
		String value = "";
		Object[] evaluateXPath;
		try {
			evaluateXPath = rootNode.evaluateXPath(xpath);
			if(evaluateXPath.length>0){
				TagNode tagNode = (TagNode)evaluateXPath[0];
				value = tagNode.getAttributeByName(attr);
			}
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		return value;
	}
	

}
