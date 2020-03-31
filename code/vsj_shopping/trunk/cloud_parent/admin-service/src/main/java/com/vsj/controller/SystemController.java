package com.vsj.controller;

import com.alibaba.fastjson.JSON;
import com.vsj.common.BaseController;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.SysConfigList;
import com.vsj.common.model.request.SysConfigType;
import com.vsj.common.utils.StringUtil;
import com.vsj.service.ISystemService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * @ClassName: SystemController
 * @Description: 系统配置
 * @author: mario
 * @date: 2019年7月26日 下午6:47:00
 * @copyright: 青岛微视角文化传媒有限公司
 */
@RestController
@RequestMapping(value = "api/v1/system", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin
public class SystemController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ISystemService systemServiceImpl;

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "修改系统配置", notes = "系统配置信息", httpMethod = "POST")
	@PostMapping("update")
	public BaseResponseParam updateSysConfig(@RequestBody SysConfigList sysConfigList) {
		logger.info("接收到修改系统配置信息请求,请求参数={}", JSON.toJSONString(sysConfigList));
		if (StringUtil.isEmptyList(sysConfigList.getVsjSysConfigList())) {
			logger.info("接收到修改系统配置信息列表为空...");
			return BaseResponseParam.fail();
		}
		if(null == getPlatformCode()){
			logger.info("请求没有携带平台码platformCode");
			return BaseResponseParam.fail("请刷新重试");
		}
		long startTime = System.currentTimeMillis();
		BaseResponseParam response = systemServiceImpl.updateSysConfig(sysConfigList.getVsjSysConfigList(),
				getPlatformCode());
		logger.info("修改系统配置信息请求完成,返回参数={},耗时={}", response, (System.currentTimeMillis() - startTime));
		return response;
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "查询系统配置信息", notes = "查询系统配置信息", httpMethod = "POST")
	@ApiImplicitParams({ @ApiImplicitParam(name = "type", value = "查询类型", required = true) })
	@PostMapping("query")
	public BaseResponseParam selectSysConfigList(@RequestBody SysConfigType sysConfigType) {
		logger.info("接收到查询系统配置信息请求,查询类型={}", sysConfigType);
		long startTime = System.currentTimeMillis();
		if (null == sysConfigType || null == sysConfigType.getType()) {
			logger.info("接收到查询系统配置信息类型为空...");
			return BaseResponseParam.fail();
		}
		BaseResponseParam response = systemServiceImpl.selectSysConfigList(sysConfigType.getType(), getPlatformCode());
		logger.info("查询系统配置信息请求完成,返回参数={},耗时={}", response, (System.currentTimeMillis() - startTime));
		return response;
	}
}
