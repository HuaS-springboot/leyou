package com.vsj.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vsj.common.constant.SysDictionaryTypeConstant;
import com.vsj.common.redis.client.RedisClient;
import com.vsj.common.redis.constant.RedisKeyConstant;
import com.vsj.common.utils.StringUtil;
import com.vsj.mapper.VsjSysDictionaryMapper;
import com.vsj.model.VsjSysDictionary;

@Component
public class VsjSysDictionaryDAO {
	
	@Autowired
	private VsjSysDictionaryMapper vsjSysDictionaryMapper;
	
	@Autowired
	private RedisClient redisClient;
	
	public Map<String,Map<String,String>> selectSysDictionary(){
		Map<String,Map<String,String>> resultMap = new HashMap<>();
		String[] dictionaryType = SysDictionaryTypeConstant.DICTIONARY;
		Map<String,String> map = null;
		for(String type : dictionaryType){
			map = selectByType(type);
			if(!StringUtil.isEmptyMap(map)){
				resultMap.put(type, map);
			}
		}
		return resultMap;
	}
	
	public Map<String,String> selectByType(String type){
		Map<String,String> resultMap = (Map<String,String>)redisClient.hmGet(RedisKeyConstant.SYS_DICTIONARY_KEY, type);
		if(StringUtil.isEmptyMap(resultMap)){
			List<VsjSysDictionary> result = vsjSysDictionaryMapper.selectByType(type);
			if(!StringUtil.isEmptyList(result)){
				Map<String,String > map = new HashMap<>(); 
				result.forEach((r) -> map.put(r.getKey(), r.getValue()));
				redisClient.hmSet(RedisKeyConstant.SYS_DICTIONARY_KEY, type, map);
				return map;
			}
		}
		return resultMap;
	}
}
