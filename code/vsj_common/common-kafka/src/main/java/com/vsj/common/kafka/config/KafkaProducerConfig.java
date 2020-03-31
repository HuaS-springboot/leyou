package com.vsj.common.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;
/**
 * 
 * 
 * @ClassName: KafkaProducerConfig.class
 * @Description: Kafka生产者配置类
 * @version: v1.0.0
 * @author: mario  
 * @date: 2018年6月20日
 * <br/>Modification History: <br/>
 * Date         Author          Version            Description <br/>
 *---------------------------------------------------------* <br/>
 * 2018年6月20日     mario          v1.0.0               修改原因 <br/>
 *
 */
@Component
@Configuration
@EnableKafka
@PropertySource(value = "classpath:kafka.properties",encoding = "utf-8")
public class KafkaProducerConfig {
	@Value("${kafka.producer.servers}")
	private String servers;
	@Value("${kafka.producer.retries}")
	private int retries;
	@Value("${kafka.producer.batch.size}")
	private int batchSize;
	@Value("${kafka.producer.linger}")
	private int linger;
	@Value("${kafka.producer.buffer.memory}")
	private int bufferMemory;

	@SuppressWarnings("rawtypes")
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate(producerFactory());
	}

	public ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<String, String>(producerConfigs());
	}
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<String, Object>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
		props.put(ProducerConfig.RETRIES_CONFIG, retries);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
		props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return props;
	}
	
}
