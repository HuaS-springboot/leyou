package com.vsj.job.service.impl;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vsj.dao.JobAndTriggerDAO;
import com.vsj.job.BaseJob;
import com.vsj.job.service.IJobAndTriggerService;
import com.vsj.model.JobAndTrigger;


@Service
public class JobAndTriggerImpl implements IJobAndTriggerService{
	private static final Logger logger = LoggerFactory.getLogger(JobAndTriggerImpl.class);
	//加入Qulifier注解，通过名称注入bean
//	@Autowired @Qualifier("Scheduler")
//	private Scheduler scheduler;
	
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	
	@Autowired
	private JobAndTriggerDAO jobAndTriggerDAO;
	
	public PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<JobAndTrigger> list = jobAndTriggerDAO.getJobAndTriggerDetails();
		PageInfo<JobAndTrigger> page = new PageInfo<JobAndTrigger>(list);
		return page;
	}

	@Override
	public void addJob(String name ,String jobClassName, String jobGroupName, String cronExpression) throws Exception {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		// 启动调度器  
		//scheduler.start(); 
		
		//构建job信息
		JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobClassName, jobGroupName).build();
		
		//表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName).withSchedule(scheduleBuilder).build();
        try {
        	scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
        	logger.error("创建定时任务失败...{}",e);
        }
		//修改定时器名称
        JobAndTrigger jobAndTrigger = new JobAndTrigger();
        jobAndTrigger.setNAME(name);
        jobAndTrigger.setTRIGGERNAME(jobClassName);
        jobAndTrigger.setTRIGGERGROUP(jobGroupName);
        jobAndTriggerDAO.updateName(jobAndTrigger);
	}
	
	private static BaseJob getClass(String classname) throws Exception{
		Class<?> class1 = Class.forName(classname);
		return (BaseJob)class1.newInstance();
	}

	@Override
	public void jobdelete(String jobClassName, String jobGroupName) throws Exception {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
		scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
		scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));			
	}

	@Override
	public void jobreschedule(String name ,String jobClassName, String jobGroupName, String cronExpression) throws Exception {
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		} catch (SchedulerException e) {
			logger.error("更新定时任务失败...{}",e);
		}
		//修改定时器名称
        JobAndTrigger jobAndTrigger = new JobAndTrigger();
        jobAndTrigger.setNAME(name);
        jobAndTrigger.setTRIGGERNAME(jobClassName);
        jobAndTrigger.setTRIGGERGROUP(jobGroupName);
        jobAndTriggerDAO.updateName(jobAndTrigger);
	}

	@Override
	public void jobresume(String jobClassName, String jobGroupName) throws Exception {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
	}

	@Override
	public void jobPause(String jobClassName, String jobGroupName) throws Exception {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
	}

}