package com.vsj.common.service.impl;

import com.vsj.common.helper.SendMessageHelper;
import com.vsj.common.model.VerificationCodeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.vsj.common.constants.SysConfigConstants;
import com.vsj.common.enums.ShortMessageTypeEnum;
import com.vsj.common.model.AliVerificationResParam;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.redis.client.RedisClient;
import com.vsj.common.redis.constant.RedisKeyConstant;
import com.vsj.common.service.IShortMessagingService;
import com.vsj.common.utils.StringUtil;
import com.vsj.dao.VsjSysConfigDAO;
import com.vsj.model.VsjSysConfig;

@SuppressWarnings("rawtypes")
@Service
public class ShortMessagingServiceImpl implements IShortMessagingService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 验证码过期时间
	 */
	private static final long expireTime = 60l;
	
	@Autowired
	private SendMessageHelper sendMessageHelper;
	
	@Autowired
	private VsjSysConfigDAO vsjSysConfigDAO;
	
	@Autowired
	private RedisClient redisClient;
	
	@Override
	public BaseResponseParam sendMessage(String telPhoneNum,byte type,int platformCode) {
		
		String templateCode = null;
		String signName = null;
		String accessKeyId = null;
		String secret = null;
		VsjSysConfig templateCodeConfig = null;
		VsjSysConfig signNameCodeConfig = null;
		VsjSysConfig accessKeyIdConfig = null;
		VsjSysConfig secretConfig = null;
		
		//查询短信模板配置
		if(ShortMessageTypeEnum.REGISTE.getCode() == type){
			templateCodeConfig = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.ALI_DX_REGIST_TEMPLATE, platformCode);
		}else if(ShortMessageTypeEnum.FIND.getCode() == type){
			templateCodeConfig = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.ALI_DX_FIND_TEMPLATE, platformCode);
		}
		if(null != templateCodeConfig && StringUtil.isNoEmptyStr(templateCodeConfig.getValue())){
			templateCode = templateCodeConfig.getValue();
		}
		if(StringUtil.isNoEmptyStr(templateCode)){
			logger.info("平台Code={},未查询到短信模板配置...",platformCode);
			return BaseResponseParam.fail();
		}
		//查询短信签名配置
		signNameCodeConfig = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.ALI_DX_SIGN, platformCode);
		if(null != signNameCodeConfig && StringUtil.isNoEmptyStr(signNameCodeConfig.getValue())){
			signName = signNameCodeConfig.getValue();
		}
		if(StringUtil.isNoEmptyStr(signName)){
			logger.info("平台Code={},未查询到短信签名配置...",platformCode);
			return BaseResponseParam.fail();
		}
		//短信keyId配置
		accessKeyIdConfig = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.ALI_DX_SIGN, platformCode);
		if(null != accessKeyIdConfig && StringUtil.isNoEmptyStr(accessKeyIdConfig.getValue())){
			accessKeyId = accessKeyIdConfig.getValue();
		}
		if(StringUtil.isNoEmptyStr(accessKeyId)){
			logger.info("平台Code={},未查询到短信accessKeyId配置...",platformCode);
			return BaseResponseParam.fail();
		}
		//短信keySecret配置
		secretConfig = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.ALI_DX_SIGN, platformCode);
		if(null != secretConfig && StringUtil.isNoEmptyStr(secretConfig.getValue())){
			secret = secretConfig.getValue();
		}
		if(StringUtil.isNoEmptyStr(secret)){
			logger.info("平台Code={},未查询到短信accessKeySecret配置...",platformCode);
			return BaseResponseParam.fail();
		}
		String verificationCode = StringUtil.getVerificationCode();
		boolean result = sendMessageHelper.send(telPhoneNum, templateCode, signName, VerificationCodeModel.json(verificationCode), accessKeyId, secret);
		if(result){
			//发送成功
			//存redis,1分钟失效
			redisClient.set(String.format(RedisKeyConstant.SYSTEM_CONFIG_KEY, telPhoneNum),verificationCode, expireTime);
			return BaseResponseParam.success(verificationCode);
		}
		return BaseResponseParam.fail();
	}

}
