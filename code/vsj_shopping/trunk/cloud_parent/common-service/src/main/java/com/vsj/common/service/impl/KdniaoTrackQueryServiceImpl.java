package com.vsj.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.vsj.common.constants.SysConfigConstants;
import com.vsj.common.handler.KdniaoTrackQueryHandler;
import com.vsj.common.model.KdniaoResponseParam;
import com.vsj.common.service.IKdniaoTrackQueryService;
import com.vsj.common.utils.StringUtil;
import com.vsj.dao.VsjSysConfigDAO;
import com.vsj.model.VsjSysConfig;

@Service
public class KdniaoTrackQueryServiceImpl implements IKdniaoTrackQueryService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private KdniaoTrackQueryHandler kdniaoTrackQueryHandler;
	
	@Autowired
	private VsjSysConfigDAO vsjSysConfigDAO;

	@Override
	public KdniaoResponseParam queryTrackInfo(String expCode, String expNo, int platformCode) {
		VsjSysConfig userIdConfig = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.SYS_KDN_USER_ID, platformCode);
		VsjSysConfig apiKeyConfig = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.SYS_KDN_API_KEY, platformCode);
		if(null == userIdConfig || null == apiKeyConfig || StringUtil.isEmptyStr(userIdConfig.getValue())|| StringUtil.isEmptyStr(apiKeyConfig.getValue())){
			logger.debug("根据platformCode={},未查询到快递鸟配置信息...",platformCode);
			return null;
		}
		String jsonResult = kdniaoTrackQueryHandler.getOrderTraces(userIdConfig.getValue(), apiKeyConfig.getValue(), expCode, expNo);
		if(StringUtil.isEmptyStr(jsonResult)){
			logger.debug("根据快递单={},快递公司={}未查询到快递信息...",expCode,expNo);
			return null;
		}
		logger.debug("根据快递单号={}查询到的物流信息={}",expNo,jsonResult);
		return JSON.parseObject(jsonResult,KdniaoResponseParam.class);
	}
	
	
	
}
