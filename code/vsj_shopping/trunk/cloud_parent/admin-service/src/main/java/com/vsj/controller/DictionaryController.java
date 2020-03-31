package com.vsj.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.service.IDictionaryService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "api/v1/dictionary", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin
public class DictionaryController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IDictionaryService dictionaryServiceImpl;
	
	@ApiOperation(value = "查询数据字典", notes = "查询数据字典", httpMethod = "POST")
	@ApiImplicitParams({ @ApiImplicitParam(name = "type", value = "查询类型", required = true) })
	@GetMapping("query")
	public BaseResponseParam<Map<String,Map<String,String>>> selectSysConfigList() {
		logger.info("接收到查询系统数据字典请求");
		long startTime = System.currentTimeMillis();
		BaseResponseParam<Map<String,Map<String,String>>> response = dictionaryServiceImpl.selectSysDictionary();
		logger.info("查询系统配置信息请求完成,返回参数={},耗时={}", response, (System.currentTimeMillis() - startTime));
		return response;
	}
}
