package com.test.spider;

import static org.junit.Assert.*;

import java.net.InetAddress;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.junit.Test;

public class CuratorTest {
	
	@Test
	public void test() throws Exception {
		String connectString = "192.168.1.170:2181";
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
		client.start();
		InetAddress localHost = InetAddress.getLocalHost();
		String hostAddress = localHost.getHostAddress();
		
		
		client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).withACL(Ids.OPEN_ACL_UNSAFE).forPath("/spider/"+hostAddress, "".getBytes());
		
	}

}
