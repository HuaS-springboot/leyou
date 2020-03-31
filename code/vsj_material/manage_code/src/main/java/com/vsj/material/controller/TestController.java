package com.vsj.material.controller;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.material.model.User;
import com.vsj.material.service.ITestService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin
public class TestController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ITestService testServiceImpl;

    @ApiOperation(value = "开始测试", notes = "开始测试", httpMethod = "POST")
    @PostMapping("test")
    public BaseResponseParam test(@RequestBody User user) {
        logger.info("开始测试,user={}",user);
        user.setPlatformCode(getPlatformCode());
        long startTime = System.currentTimeMillis();
        BaseResponseParam response = testServiceImpl.ok(user);
        logger.info("测试完成,返回参数={},耗时={}", response, (System.currentTimeMillis() - startTime));
        return response;
    }
}
