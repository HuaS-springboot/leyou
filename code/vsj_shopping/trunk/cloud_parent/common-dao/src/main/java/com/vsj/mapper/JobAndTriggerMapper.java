package com.vsj.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

import com.vsj.model.JobAndTrigger;
@Mapper
public interface JobAndTriggerMapper {

	@SelectProvider(type = Provider.class, method = "getJobAndTriggerDetails")
	List<JobAndTrigger> getJobAndTriggerDetails();

	@UpdateProvider(type = Provider.class, method = "updateName")
	int updateName(JobAndTrigger jobAndTrigger);

	class Provider {
		public String getJobAndTriggerDetails() {
			String sql = new SQL() {
				{
					SELECT("qrtz_job_details.JOB_NAME,qrtz_job_details.JOB_GROUP,qrtz_job_details.JOB_CLASS_NAME,qrtz_triggers.TRIGGER_NAME,qrtz_triggers.TRIGGER_GROUP,qrtz_triggers.TRIGGER_STATE,qrtz_triggers.NAME,qrtz_cron_triggers.CRON_EXPRESSION,qrtz_cron_triggers.TIME_ZONE_ID");
					FROM("qrtz_job_details");
					JOIN("qrtz_triggers");
					JOIN("qrtz_cron_triggers on qrtz_job_details.JOB_NAME = qrtz_triggers.JOB_NAME AND qrtz_triggers.TRIGGER_NAME = qrtz_cron_triggers.TRIGGER_NAME AND qrtz_triggers.TRIGGER_GROUP = qrtz_cron_triggers.TRIGGER_GROUP");
				}
			}.toString();
			/*StringBuffer sql = new StringBuffer();
			sql.append("select ");
			sql.append("qrtz_job_details.JOB_NAME as JOB_NAME, ");
			sql.append("qrtz_job_details.JOB_GROUP, ");
			sql.append("qrtz_job_details.JOB_CLASS_NAME, ");
			sql.append("qrtz_triggers.TRIGGER_NAME, ");
			sql.append("qrtz_triggers.TRIGGER_GROUP, ");
			sql.append("qrtz_triggers.TRIGGER_STATE, ");
			sql.append("qrtz_triggers.NAME, ");
			sql.append("qrtz_cron_triggers.CRON_EXPRESSION, ");
			sql.append("qrtz_cron_triggers.TIME_ZONE_ID ");
			sql.append("FROM qrtz_job_details ");
			sql.append("JOIN qrtz_triggers ");
			sql.append("JOIN qrtz_cron_triggers ON qrtz_job_details.JOB_NAME = qrtz_triggers.JOB_NAME ");
			sql.append("AND qrtz_triggers.TRIGGER_NAME = qrtz_cron_triggers.TRIGGER_NAME ");
			sql.append("AND qrtz_triggers.TRIGGER_GROUP = qrtz_cron_triggers.TRIGGER_GROUP ");*/
			return sql;
		}

		public String updateName(JobAndTrigger jobAndTrigger) {
			String sql = new SQL() {
				{
					UPDATE("qrtz_triggers");
					SET("`NAME` = #{NAME}");
					WHERE("`TRIGGER_NAME` = #{TRIGGERNAME} and `TRIGGER_GROUP` = #{TRIGGERGROUP}");
				}
			}.toString();
			return sql;
		}
	}
}
