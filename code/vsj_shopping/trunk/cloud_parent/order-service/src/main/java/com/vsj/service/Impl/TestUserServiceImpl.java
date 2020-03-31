package com.vsj.service.Impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.redis.client.RedisClient;
import com.vsj.common.redis.constant.RedisKeyConstant;
import com.vsj.common.utils.StringUtil;
import com.vsj.mapper.UserMapper;
import com.vsj.model.User;
import com.vsj.model.VsjSysAreas;
import com.vsj.service.TestUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestUserServiceImpl implements TestUserService {
	private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisClient redisClient;

    @Override
    public Object listUser() {
        PageHelper.startPage(1,10);
        List<User> collection = userMapper.findAll();
        PageInfo<User> userPageInfo = new PageInfo<>(collection);
        return BaseResponseParam.success(userPageInfo);
    }

    @Override
    public User findById(int id) {
        return userMapper.get(id);
    }

    @Override
    public Object getSysAreas() {
        String redisStr = (String) redisClient.get(RedisKeyConstant.SYSTEM_AREAS_KEY);
        if(!StringUtil.isEmptyStr(redisStr)){
            List<VsjSysAreas> rootList = JSON.parseArray(redisStr,VsjSysAreas.class);
            return BaseResponseParam.success(rootList);
        }
        List<VsjSysAreas> rootList = getDbSysAreas();
        redisClient.set(RedisKeyConstant.SYSTEM_AREAS_KEY, JSON.toJSONString(rootList));
        return BaseResponseParam.success(rootList);
    }

    private List<VsjSysAreas> getDbSysAreas() {
        // 保存结构
        List<VsjSysAreas> rootList = new ArrayList<>();
        // 获取数据库数据
        List<VsjSysAreas> dbListAreas = userMapper.getSysAreas();
        // 保存到map中
        Map<String,VsjSysAreas> permissionMap = new HashMap<>();
        for (VsjSysAreas area : dbListAreas) {
            permissionMap.put(area.getCode(), area);
        }
        for (VsjSysAreas area : dbListAreas) {
            VsjSysAreas child = area;
            if ("0".equals(area.getParentCode())) {
                // 获取root
                rootList.add(area);
            } else {
                // 根据child的parentCode当做key获取父级
                VsjSysAreas parent = permissionMap.get(child.getParentCode());
                if(parent != null){
                    parent.getChildren().add(child);
                }
            }
        }
        return rootList;
    }

    @Async
	@Override
	public String test() {
    	logger.debug("xxxxxxxxxxx");
		System.out.println("xxxxxx");
		 return "aaaaa";
	}
}
