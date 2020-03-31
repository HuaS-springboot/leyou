package com.vsj.common.handler;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.vsj.common.service.IKafkaTopicConsumer;
import com.vsj.dao.KafkaConsumerDAO;
import com.vsj.model.KafkaConsumeModel;
import com.vsj.model.KafkaSendModel;

/**
 * 
 * @ClassName: KafkaConsumerHandler
 * @Description: kafka消费处理类
 * 泛型对象为具体的业务处理类
 * @author: mario 
 * @date: 2019年7月24日 下午3:24:48
 * @copyright: 青岛微视角文化传媒有限公司
 * @param <T>泛型对象为具体的业务处理类
 */
@Component
public class KafkaConsumerHandler<T extends IKafkaTopicConsumer> {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private KafkaConsumerDAO kafkaConsumerDAO;

	/**
	 * 
	 * @Title: consume
	 * @Description: kafka消费者统一处理类
	 * @param record
	 * @param cls
	 * @author mario
	 * @return: void
	 */
	@SuppressWarnings("rawtypes")
	public void consume(ConsumerRecord record,Class<T> cls) {
		try {
			T consumer = (T)cls.newInstance();
			logger.debug("接受到kafka消费,record={},cls={}...",record.toString(),cls.getName());
			KafkaSendModel kafkaSendModel = JSON.parseObject(String.valueOf(record.value()), KafkaSendModel.class);
			consumer.doConsume(kafkaSendModel.getRecord(),false);
		} catch (Exception e) {
			logger.error("消费者消费异常:{}",e);
			try {
				KafkaConsumeModel object = new KafkaConsumeModel();
				kafkaConsumerDAO.insert(object);
			} catch (Exception e2) {
				logger.error("消费者异常数据写库异常....e={}",e2);
			}
		}
	}

}
