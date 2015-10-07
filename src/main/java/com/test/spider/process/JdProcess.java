package com.test.spider.process;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.test.spider.domain.Page;
import com.test.spider.utils.HtmlUtils;
import com.test.spider.utils.PageUtils;

public class JdProcess implements Processable {

	@Override
	public void process(Page page) {
		String content = page.getContent();
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode rootNode = htmlCleaner.clean(content);
		if(page.getUrl().startsWith("http://list.jd.com/list.html")){
			String nexturl = HtmlUtils.getAttributeByName(rootNode, "href", "//*[@id=\"J_topPage\"]/a[2]");
			nexturl = "http://list.jd.com"+nexturl.replace("&amp;", "&");
			page.addUrl(nexturl);
			
			try {
				Object[] evaluateXPath = rootNode.evaluateXPath("//*[@id=\"plist\"]/ul/li/div/div[1]/a");
				for (Object object : evaluateXPath) {
					TagNode tagNode = (TagNode)object;
					page.addUrl(tagNode.getAttributeByName("href"));
				}
			} catch (XPatherException e) {
				e.printStackTrace();
			}
			
			
		}else{
			parseProduct(page, rootNode);
		}
	}
	
	/**
	 * 解析商品明细数据
	 * @param page
	 * @param rootNode
	 */
	public void parseProduct(Page page, TagNode rootNode) {
		try {
			//标题
			
			String title = HtmlUtils.getText(rootNode, "//*[@id=\"name\"]/h1");
			page.addField("title", title);
			
			//图片地址
			String picurl = HtmlUtils.getAttributeByName(rootNode, "src", "//*[@id=\"spec-n1\"]/img");
			page.addField("picurl", picurl);
			
			
			//价格
			/*evaluateXPath = rootNode.evaluateXPath("//*[@id=\"jd-price\"]");
			if(evaluateXPath.length>0){
				TagNode priceNode = (TagNode)evaluateXPath[0];
				System.out.println("价格："+priceNode.getText());
			}*/
			String url = page.getUrl();
			Pattern compile = Pattern.compile("http://item.jd.com/([0-9]+).html");
			Matcher matcher = compile.matcher(url);
			String goodsId = "";
			if(matcher.find()){
				goodsId = matcher.group(1);
			}
			page.setGoodsid("jd_"+goodsId);
			String priceJson = PageUtils.getContent("http://p.3.cn/prices/get?skuid=J_"+goodsId);
			JSONArray jsonArray = new JSONArray(priceJson);
			JSONObject object = (JSONObject)jsonArray.get(0);
			page.addField("price", object.getString("p"));
			
			//规格参数
			
			Object[] evaluateXPath = rootNode.evaluateXPath("//*[@id=\"product-detail-2\"]/table/tbody/tr");
			JSONArray specjsonArray = new JSONArray();
			for (Object tagobject : evaluateXPath) {
				TagNode tagNode = (TagNode)tagobject;
				if(!"".equals(tagNode.getText().toString().trim())){
					Object[] thevaluateXPath = tagNode.evaluateXPath("/th");
					JSONObject jsonObject = new JSONObject();
					if(thevaluateXPath.length>0){
						TagNode thtagnode = (TagNode)thevaluateXPath[0];
						jsonObject.put("name", "");
						jsonObject.put("value", thtagnode.getText().toString());
					}else{
						Object[] tdevaluateXPath = tagNode.evaluateXPath("/td");
						TagNode tdtagnode1 = (TagNode)tdevaluateXPath[0];
						TagNode tdtagnode2 = (TagNode)tdevaluateXPath[1];
						jsonObject.put("name", tdtagnode1.getText().toString());
						jsonObject.put("value", tdtagnode2.getText().toString());
					}
					specjsonArray.put(jsonObject);
				}
			}
			page.addField("spec", specjsonArray.toString());
		} catch (XPatherException e) {
			e.printStackTrace();
		}
	}

}
