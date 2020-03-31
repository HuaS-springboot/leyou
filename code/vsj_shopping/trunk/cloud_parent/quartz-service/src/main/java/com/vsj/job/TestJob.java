package com.vsj.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJob implements BaseJob{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.debug("okok---------------");
		
	}

}
