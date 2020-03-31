package com.vsj.dao;

import com.vsj.common.redis.client.RedisClient;
import com.vsj.common.redis.constant.RedisKeyConstant;
import com.vsj.mapper.VsjSysConfigMapper;
import com.vsj.model.VsjSysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class VsjSysConfigDAO {

	@Autowired
	private VsjSysConfigMapper vsjSysConfigMapper;

	@Autowired
	private RedisClient redisClient;

	/**
	 * 
	 * @Title: batchInsertOrUpdate
	 * @Description: 批量更新或修改
	 * @param vsjSysConfigs
	 * @param platformCode
	 * @return
	 * @author mario
	 * @return: int
	 */
	public int batchInsertOrUpdate(List<VsjSysConfig> vsjSysConfigs,Integer platformCode) {
		// 先改库,再清redis缓存
		int i = vsjSysConfigMapper.batchInsert(vsjSysConfigs);
		if (i > 0) {
			for(VsjSysConfig config:vsjSysConfigs){
				redisClient.hmDelete((String.format(RedisKeyConstant.SYSTEM_CONFIG_KEY, platformCode)), config.getKey());
			}
			return i;
		}
		return -1;
	}

	/**
	 * 
	 * @Title: selectByConfigName
	 * @Description: 根据key查找对应系统配置
	 * @param key 详见SysConfigConstants
	 * @param platformCode
	 * @return
	 * @author mario
	 * @return: VsjSysConfig
	 */
	public VsjSysConfig selectByConfigName(String key,Integer platformCode) {		
		VsjSysConfig sysConfig = (VsjSysConfig)redisClient.hmGet(String.format(RedisKeyConstant.SYSTEM_CONFIG_KEY, platformCode), key);
		if(null == sysConfig) {
			sysConfig = vsjSysConfigMapper.selectByKey(key,platformCode);
			if(null != sysConfig){
				redisClient.hmSet(String.format(RedisKeyConstant.SYSTEM_CONFIG_KEY, platformCode), sysConfig.getKey(), sysConfig);
			}
		}
		return sysConfig;
	}
}
