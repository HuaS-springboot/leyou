package com.vsj.job.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.vsj.common.model.request.QuartzJobReqParam;
import com.vsj.job.service.IJobAndTriggerService;
import com.vsj.model.JobAndTrigger;

@RestController
@RequestMapping(value="/static/job",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class JobController {
	@Autowired
	private IJobAndTriggerService iJobAndTriggerService;

	@PostMapping(value="/addjob")
	public void addjob(@RequestBody QuartzJobReqParam quartzJobReqParam) throws Exception{			
		iJobAndTriggerService.addJob(quartzJobReqParam.getName(),quartzJobReqParam.getJobClassName(), quartzJobReqParam.getJobGroupName(), quartzJobReqParam.getCronExpression());
	}

	@PostMapping(value="/pausejob")
	public void pausejob(@RequestBody QuartzJobReqParam quartzJobReqParam) throws Exception{			
		iJobAndTriggerService.jobPause(quartzJobReqParam.getJobClassName(), quartzJobReqParam.getJobGroupName());
	}

	@PostMapping(value="/resumejob")
	public void resumejob(@RequestBody QuartzJobReqParam quartzJobReqParam) throws Exception{			
		iJobAndTriggerService.jobresume(quartzJobReqParam.getJobClassName(), quartzJobReqParam.getJobGroupName());
	}

	@PostMapping(value="/reschedulejob")
	public void rescheduleJob(@RequestBody QuartzJobReqParam quartzJobReqParam) throws Exception{			
		iJobAndTriggerService.jobreschedule(quartzJobReqParam.getName(),quartzJobReqParam.getJobClassName(), quartzJobReqParam.getJobGroupName(), quartzJobReqParam.getCronExpression());
	}
	@RequestMapping(value = "/deletejob", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void deletejob(@RequestBody QuartzJobReqParam quartzJobReqParam) throws Exception{			
		iJobAndTriggerService.jobdelete(quartzJobReqParam.getJobClassName(), quartzJobReqParam.getJobGroupName());
	}

	@RequestMapping(value = "/queryjob", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> queryjob(@RequestParam(value="pageNum")Integer pageNum, @RequestParam(value="pageSize")Integer pageSize){			
		PageInfo<JobAndTrigger> jobAndTrigger = iJobAndTriggerService.getJobAndTriggerDetails(pageNum, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("JobAndTrigger", jobAndTrigger);
		map.put("number", jobAndTrigger.getTotal());
		return map;
	}
	
}
