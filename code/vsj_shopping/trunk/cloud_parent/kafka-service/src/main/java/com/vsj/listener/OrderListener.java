package com.vsj.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.vsj.common.handler.KafkaConsumerHandler;
import com.vsj.consumer.OrderTopicConsumer;


@Component
public class OrderListener {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private KafkaConsumerHandler<OrderTopicConsumer> kafkaConsumerHandler;
	
	//单条消费
	@KafkaListener(groupId = "group1" ,topics = {"${kafka.topic.order}"}, containerFactory = "kafkaListenerContainerFactory")
	public void cloudSeatEventTopic(@SuppressWarnings("rawtypes") ConsumerRecord record, Acknowledgment ack) {
		try {
			kafkaConsumerHandler.consume(record, OrderTopicConsumer.class);
		} catch (Exception e) {
			logger.error("kafka消费异常,主题={},exception={}",record.topic(),e);
		} finally {
			ack.acknowledge();// 手动提交偏移量
		}
	}

}
