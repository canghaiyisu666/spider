package com.test.spider;

import java.util.ArrayList;
import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * 守护进程，需要一直运行
 * @author Administrator
 *
 */
public class SpiderWatcher implements Watcher {
	CuratorFramework client;
	List<String> children = new ArrayList<String>();
	public SpiderWatcher() {
		String connectString = "192.168.1.170:2181";
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
		client.start();
		
		try {
			children = client.getChildren().usingWatcher(this).forPath("/spider");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

	@Override
	public void process(WatchedEvent event) {
		try {
			List<String> newChildren = client.getChildren().usingWatcher(this).forPath("/spider");
			for (String ip : children) {
				if(!newChildren.contains(ip)){
					System.out.println("消失的节点IP："+ip);
					//TODO  给管理员发发送邮件或者短信 发邮件的话可以使用javamail 发短信的话可以使用第三方服务，例如：云片
				}
			}
			
			for (String ip : newChildren) {
				if(!children.contains(ip)){
					System.out.println("新增的节点IP："+ip);
				}
			}
			//这一行代码非常和重要
			this.children = newChildren;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		SpiderWatcher spiderWatcher = new SpiderWatcher();
		spiderWatcher.run();
	}


	private void run() {
		while(true){
			;
		}
	}
	
	
	
	

}
