package com.test.spider;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class UrlManager {
	
	public static void main(String[] args) {
		try {
			Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
			defaultScheduler.start();
			
			String simpleName = UrlJob.class.getSimpleName();
			JobDetail jobDetail = new JobDetail(simpleName, Scheduler.DEFAULT_GROUP, UrlJob.class);
			
			Trigger trigger = new CronTrigger(simpleName, Scheduler.DEFAULT_GROUP, "0 0 01 * * ?  ");
			
			
			
			defaultScheduler.scheduleJob(jobDetail , trigger);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	

}
