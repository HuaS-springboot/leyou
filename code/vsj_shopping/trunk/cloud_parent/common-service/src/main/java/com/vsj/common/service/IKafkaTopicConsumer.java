package com.vsj.common.service;

public interface IKafkaTopicConsumer {
	void doConsume(String record , boolean isTask) throws Exception;
}
