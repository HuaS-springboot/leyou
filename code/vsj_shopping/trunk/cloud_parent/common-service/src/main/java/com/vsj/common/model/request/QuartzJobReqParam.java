package com.vsj.common.model.request;

import java.io.Serializable;

/**
 * 
 * @ClassName: QuartzJobReqParam
 * @Description: 定时任务请求对象
 * @author: mario 
 * @date: 2019年7月31日 下午5:54:59
 * @copyright: 青岛微视角文化传媒有限公司
 */
public class QuartzJobReqParam implements Serializable{

	private static final long serialVersionUID = -7083032203927218706L;
	
	private String jobClassName;
	
	private String jobGroupName;
	
	private String cronExpression;
	
	private String name;
	
	public String getJobClassName() {
		return jobClassName;
	}
	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}
	public String getJobGroupName() {
		return jobGroupName;
	}
	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
