package com.vsj.material.dao;

import com.vsj.common.redis.client.RedisClient;
import com.vsj.common.redis.constant.RedisKeyConstant;
import com.vsj.material.mapper.VsjMaterialSysConfigMapper;
import com.vsj.material.model.VsjMaterialSysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname VsjMaterialSysConfigDAO
 * @Description 系统配置dao
 * @Date 2019/8/13 11:54
 * @Created by wangzx
 */
@Component
public class VsjMaterialSysConfigDAO {

    @Autowired
    private RedisClient redisClient;
    @Autowired
    private VsjMaterialSysConfigMapper vsjMaterialSysConfigMapper;

    public VsjMaterialSysConfig selectByConfigName(String key, Integer platformCode) {
        VsjMaterialSysConfig sysConfig = (VsjMaterialSysConfig)redisClient.hmGet(String.format(RedisKeyConstant.VSJ_MATERIAL_SYS_CONFIG_KEY, platformCode), key);
        if(null == sysConfig) {
            sysConfig = vsjMaterialSysConfigMapper.selectByKey(key,platformCode);
            if(null != sysConfig){
                redisClient.hmSet(String.format(RedisKeyConstant.VSJ_MATERIAL_SYS_CONFIG_KEY, platformCode), sysConfig.getKey(), sysConfig);
            }
        }
        return sysConfig;
    }

    public void updateSysConfig(List<VsjMaterialSysConfig> materialSysConfigList,Integer platformCode) {
        for (VsjMaterialSysConfig vsjMaterialSysConfig : materialSysConfigList) {
            int count = vsjMaterialSysConfigMapper.updateSysConfig(vsjMaterialSysConfig);
            if(count > 0){
                redisClient.hmDelete((String.format(RedisKeyConstant.VSJ_MATERIAL_SYS_CONFIG_KEY, platformCode)), vsjMaterialSysConfig.getKey());
            }
        }
    }
}
