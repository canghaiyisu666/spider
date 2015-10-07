package com.test.spider;

import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.tools.javac.comp.Check;
import com.test.spider.domain.Page;
import com.test.spider.download.Downloadable;
import com.test.spider.download.HttpClientDownload;
import com.test.spider.process.JdProcess;
import com.test.spider.process.Processable;
import com.test.spider.repository.QueueRepository;
import com.test.spider.repository.RedisRepository;
import com.test.spider.repository.Repository;
import com.test.spider.store.ConsoleStore;
import com.test.spider.store.HbaseStore;
import com.test.spider.store.Storeable;
import com.test.spider.threadpool.FixedThreadPool;
import com.test.spider.threadpool.ThreadPool;
import com.test.spider.utils.Config;
import com.test.spider.utils.SleepUtils;

public class Spider {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	public Spider() {
		String connectString = "192.168.1.170:2181";
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
		client.start();
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			String hostAddress = localHost.getHostAddress();
			client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).withACL(Ids.OPEN_ACL_UNSAFE).forPath("/spider/"+hostAddress, "".getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	private Downloadable downloadable = new HttpClientDownload();
	
	private Processable processable;
	
	private Storeable storeable = new ConsoleStore();
	
	
	private Repository repository = new QueueRepository();
	 
	private ThreadPool threadPool = new FixedThreadPool();
	
	public void start() {
		
		check();
		logger.info("爬虫开始爬取数据...");
		while(true){
			final String url = repository.poll();
			if(StringUtils.isNotBlank(url)){
				threadPool.execute(new Runnable() {
					public void run() {
						// 下载
						Page page = Spider.this.download(url);
						// 解析
						Spider.this.process(page);
						List<String> urlList = page.getUrlList();
						for (String nexturl : urlList) {
							if (nexturl
									.startsWith("http://list.jd.com/list.html")) {
								repository.addHigh(nexturl);
							} else {
								repository.add(nexturl);
							}
						}
						// 存储
						if (url.startsWith("http://item.jd.com/")) {
							Spider.this.store(page);
						}
						System.out.println("当前线程ID："+Thread.currentThread().getId());
					}
				});
				SleepUtils.sleep(Config.million_1);
			}else{
				System.out.println("沒有url了，休息一会。");
				SleepUtils.sleep(Config.million_5);
			}
		}
	}
	
	/**
	 * 检查爬虫的配置
	 */
	private void check() {
		if(processable==null){
			String message = "没有配置爬虫解析类...";
			logger.error(message);
			throw new RuntimeException(message);
		}
		logger.info("==================================================");
		logger.info("downloadable的实现类：{}",downloadable.getClass().getSimpleName());
		logger.info("processable的实现类：{}",processable.getClass().getSimpleName());
		logger.info("storeable的实现类：{}",storeable.getClass().getSimpleName());
		logger.info("repository的实现类：{}",repository.getClass().getSimpleName());
		logger.info("threadPool的实现类：{}",threadPool.getClass().getSimpleName());
		logger.info("==================================================");
		
	}

	/**
	 * 下载网页
	 * @param url
	 */
	public Page download(String url) {
		Page page = this.downloadable.download(url);
		return page;
	}
	
	/**
	 * 解析网页内容
	 * @param page 
	 */
	public void process(Page page) {
		this.processable.process(page);
	}
	/**
	 * 保存网页内容
	 * @param page 
	 */
	public void store(Page page) {
		this.storeable.store(page);
	}

	public Downloadable getDownloadable() {
		return downloadable;
	}

	public void setDownloadable(Downloadable downloadable) {
		this.downloadable = downloadable;
	}

	public Processable getProcessable() {
		return processable;
	}

	public void setProcessable(Processable processable) {
		this.processable = processable;
	}

	public Storeable getStoreable() {
		return storeable;
	}

	public void setStoreable(Storeable storeable) {
		this.storeable = storeable;
	}
	
	public void setSeedUrl(String url){
		this.repository.add(url);
	}
	
	
	
	public ThreadPool getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(ThreadPool threadPool) {
		this.threadPool = threadPool;
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public static void main(String[] args) {
		Spider spider = new Spider();
		spider.setProcessable(new JdProcess());
		spider.setStoreable(new HbaseStore());
		String url = "http://list.jd.com/list.html?cat=9987,653,655";
		spider.setSeedUrl(url); 
		spider.start();
		
	}
	
	
}
