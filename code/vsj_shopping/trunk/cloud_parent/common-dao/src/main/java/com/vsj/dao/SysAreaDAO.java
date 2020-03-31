package com.vsj.dao;

import com.alibaba.fastjson.JSON;
import com.vsj.common.redis.client.RedisClient;
import com.vsj.common.redis.constant.RedisKeyConstant;
import com.vsj.mapper.DistTemplateMapper;
import com.vsj.mapper.SysAreaMapper;
import com.vsj.model.VsjSysAreas;
import com.vsj.model.VsjSysDistTemplate;
import com.vsj.model.request.BaseQueryStat;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname SysAreaDao
 * @Description TODO
 * @Date 2019/7/29 11:28
 * @Created by wangzx
 */
@Component
public class SysAreaDAO {

    @Autowired
    private SysAreaMapper sysAreaMapper;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private DistTemplateMapper distTemplateMapper;

    public List<VsjSysAreas> getSysAreasList(BaseQueryStat baseQueryStat) {
        List<VsjSysAreas> redisAreas = getRedisSysArea();
        if(redisAreas != null){
            return redisAreas;
        }
        List<VsjSysAreas> list = getBdSysAreas(baseQueryStat);
        redisClient.set(RedisKeyConstant.SYSTEM_AREAS_KEY,JSON.toJSONString(list));
        return list;
    }

    private List<VsjSysAreas> getBdSysAreas(BaseQueryStat baseQueryStat) {
        // 保存结构
        List<VsjSysAreas> rootList = new ArrayList<>();
        // 获取数据库数据
        List<VsjSysAreas> dbListAreas = sysAreaMapper.getSysAreas();
        // 保存到map中
        Map<String, VsjSysAreas> permissionMap = new HashMap<>();
        // 获取设置的配送模板
        // List<VsjSysDistTemplate> templateList = distTemplateMapper.getDistTemplateList(baseQueryStat);
        for (VsjSysAreas area : dbListAreas) {
            VsjSysAreas child = area;
            // 校验已有配送模板
            // getExistDistTemplate(templateList, child);
            permissionMap.put(area.getCode(), area);
            if ("0".equals(area.getParentCode())) {
                // 获取root
                rootList.add(area);
            } else {
                // 根据child的parentCode当做key获取父级
                VsjSysAreas parent = permissionMap.get(child.getParentCode());
                if (parent != null) {
                    parent.getChildren().add(child);
                }
            }
        }
        return rootList;
    }

    /*private void getExistDistTemplate(List<VsjSysDistTemplate> templateList, VsjSysAreas child) {
        for (VsjSysDistTemplate template : templateList) {
            if (StringUtils.isNotBlank(template.getProvinceCode()) && StringUtils.isNotBlank(template.getCityCode())) {
                // 省下的某个市有模板
                if (child.getCode().equals(template.getProvinceCode()) || child.getCode().equals(template.getCityCode())) {
                    child.setStatus(1);
                }
            }
            if (StringUtils.isNotBlank(template.getProvinceCode()) && StringUtils.isBlank(template.getCityCode())) {
                // 整个市都有模板
                if (child.getCode().equals(template.getProvinceCode()) || child.getParentCode().equals(template.getProvinceCode())) {
                    child.setStatus(1);
                }
            }
        }
    }*/

    private List<VsjSysAreas> getRedisSysArea() {
        String redisStr = (String) redisClient.get(RedisKeyConstant.SYSTEM_AREAS_KEY);
        return JSON.parseArray(redisStr, VsjSysAreas.class);
    }
}
