package com.vsj.job.service;


import com.github.pagehelper.PageInfo;
import com.vsj.model.JobAndTrigger;

public interface IJobAndTriggerService {
	
	PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize);
	/**
	 * 
	 * @Title: addJob   
	 * @Description: 添加任务
	 * @param: @param jobClassName
	 * @param: @param jobGroupName
	 * @param: @param cronExpression
	 * @param: @throws Exception      
	 * @return: void      
	 * @throws
	 */
	void addJob(String name, String jobClassName, String jobGroupName, String cronExpression)throws Exception;
	/**
	 * 
	 * @Title: jobdelete   
	 * @Description: 删除任务
	 * @param: @param jobClassName
	 * @param: @param jobGroupName
	 * @param: @throws Exception      
	 * @return: void      
	 * @throws
	 */
	void jobdelete(String jobClassName, String jobGroupName) throws Exception;
	/**
	 * 
	 * @Title: jobreschedule   
	 * @Description: 更新任务
	 * @param: @param jobClassName
	 * @param: @param jobGroupName
	 * @param: @param cronExpression
	 * @param: @throws Exception      
	 * @return: void      
	 * @throws
	 */
	void jobreschedule(String name,String jobClassName, String jobGroupName, String cronExpression) throws Exception;
	/**
	 * 
	 * @Title: jobresume   
	 * @Description: 恢复任务 
	 * @param: @param jobClassName
	 * @param: @param jobGroupName
	 * @param: @throws Exception      
	 * @return: void      
	 * @throws
	 */
	void jobresume(String jobClassName, String jobGroupName) throws Exception;
	/**
	 * 
	 * @Title: jobPause   
	 * @Description: 暂停任务
	 * @param: @param jobClassName
	 * @param: @param jobGroupName
	 * @param: @throws Exception      
	 * @return: void      
	 * @throws
	 */
	void jobPause(String jobClassName, String jobGroupName) throws Exception;
}
