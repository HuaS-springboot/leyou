package com.vsj.common.kafka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@PropertySource(value = "classpath:kafka.properties",encoding = "utf-8")
public class KafkaTopicParam {
	
	/**
	 * 测试主题
	 */
	@Value("${kafka.topic.test}")
    private String testTopic;

	public String getTestTopic() {
		return testTopic;
	}
	
	
	
}
