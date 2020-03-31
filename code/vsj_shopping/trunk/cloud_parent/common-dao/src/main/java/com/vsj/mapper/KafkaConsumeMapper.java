package com.vsj.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import com.vsj.model.KafkaConsumeModel;

@Mapper
public interface KafkaConsumeMapper {
	
	public static final String tableName = "vsj_kafka_consume";
	
	@InsertProvider(type = KafkaConsumeMapper.KafkaConsumeProvider.class, method = "insert")
	int insert(KafkaConsumeModel kafkaConsumeModel);
	
	
	class KafkaConsumeProvider {
		public String insert(KafkaConsumeModel kafkaConsumeModel) {
			String sql = new SQL() {
				{
					INSERT_INTO(tableName);
					// 必填字段
					VALUES("topic", "#{topic}");
					VALUES("record", "#{record}");
					VALUES("consumer_class", "#{consumerClass}");
					// 非必填
					if (null != kafkaConsumeModel.getStatus()) {
						VALUES("status", "#{status}");
					}
					if (null != kafkaConsumeModel.getNum()) {
						VALUES("num", "#{num}");
					}
					if (null != kafkaConsumeModel.getCreateTime()) {
						VALUES("create_time", "#{createTime}");
					}
					if (null != kafkaConsumeModel.getUpdateTime()) {
						VALUES("update_time", "#{updateTime}");
					}
				}
			}.toString();
			return sql;
		}
	}
}
