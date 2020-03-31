package com.vsj.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vsj.mapper.KafkaConsumeMapper;
import com.vsj.model.KafkaConsumeModel;

@Component
public class KafkaConsumerDAO {
	@Autowired
	private KafkaConsumeMapper kafkaConsumeMapper;
	
	public int insert(KafkaConsumeModel kafkaConsumeModel){
		return kafkaConsumeMapper.insert(kafkaConsumeModel);
	}
}
