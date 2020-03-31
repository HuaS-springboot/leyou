 package com.vsj.common.handler;

import java.util.concurrent.ExecutionException;

import com.vsj.model.KafkaSendModel;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.support.SendResult;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vsj.common.kafka.KafkaProducer;
import com.vsj.dao.KafkaSendDAO;


/**
 * 
 * @ClassName: KafkaSender
 * @Description: Kafka发送工具类
 * (多例)
 * @author: mario 
 * @date: 2019年7月31日 下午5:49:31
 * @copyright: 青岛微视角文化传媒有限公司
 */
@Component
@Scope("prototype")
public class KafkaSender extends KafkaProducer{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private KafkaSendDAO kafkaSendDAO;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void onSuccess(Object result) {
		long startTime = System.currentTimeMillis();
		ProducerRecord producerRecord = ((SendResult) result).getProducerRecord();
		String value = producerRecord.value().toString();
		logger.debug("kafka发送成功,回调数据={}",value);
		JSONObject valueObject = JSONObject.parseObject(value);
		Integer id=valueObject.getInteger("id");
		logger.debug("开始删除...id={}",id);
		kafkaSendDAO.deleteByPrimaryKey(id);
		logger.debug("成功回调处理完成,耗时={}",(System.currentTimeMillis()-startTime));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onFailure(Throwable ex) {
		long startTime = System.currentTimeMillis();
		logger.debug("kafka发送失败,回调数据={}",JSON.toJSONString(ex));
		ProducerRecord producerRecord = ((KafkaProducerException) ex).getProducerRecord();
		String value = producerRecord.value().toString();
		KafkaSendModel kafkaSendModel = JSONObject.parseObject(value, KafkaSendModel.class);
		Integer id= kafkaSendModel.getId();
		logger.debug("开始更新kafka记录失败次数...id={}",id);
		kafkaSendDAO.updateFileCount(id);
		logger.debug("失败回调处理完成,耗时={}",(System.currentTimeMillis()-startTime));
		 
	}

	@Override
	public void asyncSendMessage(String topic, Object data) {
		KafkaSendModel object = null;
		try {
			object = new KafkaSendModel(topic,data);
			kafkaSendDAO.insert(object);
		} catch (Exception e) {
			throw new KafkaException("保存kafka发送消息入库失败", e);
		}
		super.asyncSendMessage(topic, object);
	}
	
	@Override
	public Object syncSendMessage(String topic, Object data) throws InterruptedException, ExecutionException {
		KafkaSendModel object = new KafkaSendModel(topic, data);
		return super.syncSendMessage(topic, object);
	}

	@Override
	public void asyncSendMessage(String topic, String key, Object data) {
		KafkaSendModel object = null;
		try {
			object = new KafkaSendModel(topic,data,key);
			kafkaSendDAO.insert(object);
		} catch (Exception e) {
			throw new KafkaException("保存消息实体入库失败", e);
		}
		super.asyncSendMessage(topic, key, object);
	}

	@Override
	public Object syncSendMessage(String topic, String key, Object data)
			throws InterruptedException, ExecutionException {
		KafkaSendModel object = new KafkaSendModel(topic,data,key);
		return super.syncSendMessage(topic, key, object);
	}

	@Override
	public void asyncSendMessage(String topic, int partition, String key, Object data) {
		KafkaSendModel object = null;
		try {
			object = new KafkaSendModel(topic,data,key);
			kafkaSendDAO.insert(object);
		} catch (Exception e) {
			throw new KafkaException("保存消息实体入库失败", e);
		}
		super.asyncSendMessage(topic, partition, key, object);
	}

	@Override
	public Object syncSendMessage(String topic, int partition, String key, Object data)
			throws InterruptedException, ExecutionException {
		KafkaSendModel object = new KafkaSendModel(topic,data,key);
		return super.syncSendMessage(topic, partition, key, object);
	}

	@Override
	public void asyncSendMessage(String topic, int partition, Object data) {
		KafkaSendModel object = null;
		try {
			object = new KafkaSendModel(topic,data);
			kafkaSendDAO.insert(object);
		} catch (Exception e) {
			throw new KafkaException("保存消息实体入库失败", e);
		}
		super.asyncSendMessage(topic, partition, object);
	}

	@Override
	public Object syncSendMessage(String topic, int partition, Object data)
			throws InterruptedException, ExecutionException {
		KafkaSendModel object = new KafkaSendModel(topic,data);
		return super.syncSendMessage(topic, partition, object);
	}
	
}
