package com.vsj.controller;

import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.redis.client.RedisClient;
import com.vsj.common.redis.constant.RedisKeyConstant;
import com.vsj.service.TestUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/v1/user",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TestUserController{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private TestUserService testUserService;
    
    @Autowired
	private RedisClient redisClient;

    /**
     * 获取所有用户列表
     * @return
     */
    @PostMapping("list")
    public Object list(){ return testUserService.listUser(); }

    @RequestMapping("find")
    public  Object findById(@RequestParam("id") int id){
        return testUserService.findById(id);
    }
    
    @PostMapping("ok")
    public  Object redisTest(){
    	logger.info("开始添加缓存");
    	redisClient.set(RedisKeyConstant.TEST+"15666522832", "ok");
    	logger.debug("添加缓存完成");
        return BaseResponse.success();
    }
    
    @RequestMapping("ok2")
    public  Object asyncTest(){
    	System.out.println(testUserService.test());
        return BaseResponse.success();
    }

    /**
     * 测试redis、mybatis自动映射
     * @return
     */
    @PostMapping("getSysAreas")
    public Object getSysAreas(){
        return testUserService.getSysAreas();
    }
}
