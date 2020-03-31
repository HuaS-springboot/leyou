package com.vsj.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vsj.mapper.JobAndTriggerMapper;
import com.vsj.model.JobAndTrigger;


@Component
public class JobAndTriggerDAO {
	@Autowired
	private JobAndTriggerMapper jobAndTriggerMapper;
	
	public List<JobAndTrigger> getJobAndTriggerDetails(){
		return jobAndTriggerMapper.getJobAndTriggerDetails();
	}
	
	public int updateName(JobAndTrigger jobAndTrigger){
		return jobAndTriggerMapper.updateName(jobAndTrigger);
	}
	
}
