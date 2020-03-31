package com.vsj.common.kafka;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import com.alibaba.fastjson.JSON;

/**
 * 
 * 
 * @ClassName: KafkaProducer.class
 * @Description: Kafka生产者类
 * @version: v1.0.0
 * @author: mario
 * @date: 2018年6月20日 <br/>
 *        Modification History: <br/>
 *        Date Author Version Description <br/>
 *        ---------------------------------------------------------* <br/>
 *        2018年6月20日 mario v1.0.0 修改原因 <br/>
 *
 */
public abstract class KafkaProducer implements FailureCallback, SuccessCallback {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private KafkaTemplate kafkaTemplate;

	/**
	 * 
	 * @Function: KafkaProducer.java
	 * @Description: 发送成功回调函数,有需求请重写
	 * @param result
	 * @return
	 *
	 * @version: v1.0.0
	 * @author: mario
	 * @date: 2018年6月27日
	 *
	 *        <br/>
	 *        Modification History:<br/>
	 *        Date Author Version Description <br/>
	 *        ---------------------------------------------------------* <br/>
	 *        2018年6月27日 mario v1.0.0 新增 <br/>
	 */
	public abstract void onSuccess(Object result);

	/**
	 * 
	 * @Function: KafkaProducer.java
	 * @Description: 发送失败回调函数,有需求请重写
	 * @param ex
	 * @return
	 *
	 * @version: v1.0.0
	 * @author: mario
	 * @date: 2018年6月27日
	 *
	 *        <br/>
	 *        Modification History:<br/>
	 *        Date Author Version Description <br/>
	 *        ---------------------------------------------------------* <br/>
	 *        2018年6月27日 mario v1.0.0 新增 <br/>
	 */
	public abstract void onFailure(Throwable ex);

	@SuppressWarnings("unchecked")
	public void asyncSendMessage(String topic, Object data) {
		long startTime = System.currentTimeMillis();
		logger.debug("开始异步发送kafka数据...");
		ListenableFuture listenableFuture = kafkaTemplate.send(topic, JSON.toJSONString(data));
		logger.debug("发送耗时={}....开始注册回调",(System.currentTimeMillis()-startTime));
		listenableFuture.addCallback(this, this);
		logger.debug("注册回调耗时={}",(System.currentTimeMillis()-startTime));
	}

	@SuppressWarnings("unchecked")
	public Object syncSendMessage(String topic, Object data) throws InterruptedException, ExecutionException {
		ListenableFuture listenableFuture = kafkaTemplate.send(topic, JSON.toJSONString(data));
		return listenableFuture.get();
	}

	@SuppressWarnings("unchecked")
	public void asyncSendMessage(String topic, String key, Object data) {
		ListenableFuture listenableFuture = kafkaTemplate.send(topic, key, JSON.toJSONString(data));
		listenableFuture.addCallback(this, this);
	}

	@SuppressWarnings("unchecked")
	public Object syncSendMessage(String topic, String key, Object data)
			throws InterruptedException, ExecutionException {
		ListenableFuture listenableFuture = kafkaTemplate.send(topic, key, JSON.toJSONString(data));
		return listenableFuture.get();
	}

	@SuppressWarnings("unchecked")
	public void asyncSendMessage(String topic, int partition, String key, Object data) {
		ListenableFuture listenableFuture = kafkaTemplate.send(topic, partition, key, JSON.toJSONString(data));
		listenableFuture.addCallback(this, this);
	}

	@SuppressWarnings("unchecked")
	public Object syncSendMessage(String topic, int partition, String key, Object data)
			throws InterruptedException, ExecutionException {
		ListenableFuture listenableFuture = kafkaTemplate.send(topic, partition, key, JSON.toJSONString(data));
		return listenableFuture.get();
	}

	@SuppressWarnings("unchecked")
	public void asyncSendMessage(String topic, int partition, Object data) {
		ListenableFuture listenableFuture = kafkaTemplate.send(topic, partition, JSON.toJSONString(data));
		listenableFuture.addCallback(this, this);
	}

	@SuppressWarnings("unchecked")
	public Object syncSendMessage(String topic, int partition, Object data)
			throws InterruptedException, ExecutionException {
		ListenableFuture listenableFuture = kafkaTemplate.send(topic, partition, JSON.toJSONString(data));
		return listenableFuture.get();
	}
}
