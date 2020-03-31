package com.vsj.service.impl;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.constants.SysConfigClassifyConstants;
import com.vsj.common.constants.SysConfigConstants;
import com.vsj.common.enums.ExpressAPIEnum;
import com.vsj.common.enums.RemoteFilesEnum;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.SysConfig;
import com.vsj.common.utils.StringUtil;
import com.vsj.dao.VsjSysConfigDAO;
import com.vsj.model.VsjSysConfig;
import com.vsj.service.ISystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("rawtypes")
public class SystemServiceImpl implements ISystemService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private VsjSysConfigDAO vsjSysConfigDAO;

	@Autowired
	private AbstractObjectConverter<SysConfig,VsjSysConfig> convert;


	@Override
	public BaseResponseParam updateSysConfig(List<SysConfig> sysConfigList,Integer platformCode){
		List<VsjSysConfig> vsjSysConfigList = new ArrayList<>();
		VsjSysConfig vsjSysConfig = null;
		for(SysConfig config : sysConfigList){
			//格式转换
			vsjSysConfig = convert.convert(config, VsjSysConfig.class);
			if(null != vsjSysConfig){
				//补充平台码
				vsjSysConfig.setPlatformCode(platformCode);
				vsjSysConfigList.add(vsjSysConfig);
			}
		}
		if(StringUtil.isEmptyList(vsjSysConfigList)){
			logger.debug("系统配置保存传入信息转换失败...");
			return BaseResponseParam.fail();
		}
		int result = vsjSysConfigDAO.batchInsertOrUpdate(vsjSysConfigList, platformCode);
		if(result > 0 ){
			return BaseResponseParam.success();
		}else{
			return BaseResponseParam.fail();
		}
	}

	@Override
	public BaseResponseParam selectSysConfigList(Byte type, Integer platformCode) {
		List<VsjSysConfig> results = null;
		if(type == SysConfigClassifyConstants.SHOPPING_MOUNT){
			logger.debug("开始查询商城装修配置...");
			String keys[] = SysConfigConstants.SHOPPING_GROUP;
			results = selectByKeys(keys, platformCode);
		}else if(type == SysConfigClassifyConstants.SYSTEM){
			logger.debug("开始查询系统参数配置...");
			String keys[] = SysConfigConstants.SYS_GROUP;
			results = selectByKeys(keys, platformCode);
		}else if(type == SysConfigClassifyConstants.PAY_PARAMETER){
			logger.debug("开始查询支付参数配置...");
			String keys[] = SysConfigConstants.WX_PAY_GROUP;
			results = selectByKeys(keys, platformCode);
		}else if(type == SysConfigClassifyConstants.OTHER_API){
			logger.debug("开始查询第三方API参数配置...");
			results = selectOtherAPI(platformCode);
		}else if(type == SysConfigClassifyConstants.MATERIAL){
			logger.debug("开始查询素材管理配置...");
			String keys[] = SysConfigConstants.MATERIAL_GROUP;
			results = selectByKeys(keys, platformCode);
		}
		if(StringUtil.isEmptyList(results)){
			return BaseResponseParam.fail("未查询到数据");
		}
		return BaseResponseParam.success(results);
	}
	private List<VsjSysConfig> selectOtherAPI(Integer platformCode){
		String keys[] = SysConfigConstants.OTHER_API_GROUP;
		List<VsjSysConfig> results = selectByKeys(keys, platformCode);
		//,REMOTE_FILES,SYS_KD_API
		if(!StringUtil.isEmptyList(results)){
			//判断文件存储类型是什么
			VsjSysConfig sysConfig = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.REMOTE_FILES, platformCode);
			if(null != sysConfig && StringUtil.isNoEmptyStr(sysConfig.getValue())){
			    results.add(sysConfig);
				int options = Integer.parseInt(sysConfig.getValue());
				//文件存储是配置阿里云OSS
				if(RemoteFilesEnum.ALI_OSS.getCode() == options){
					List<VsjSysConfig> ossParamResult = selectByKeys(SysConfigConstants.OSS_PARAM_GROUP, platformCode);
					if(!StringUtil.isEmptyList(results)){
						results.addAll(ossParamResult);
					}else{
						logger.info("文件存储配置为阿里云OSS存储,但未查询到对应OSS配置信息...platformCode={}",platformCode);
						return null;
					}
				}
			}else{
				logger.info("系统未选择远程文件配置...platformCode={}",platformCode);
				return null;
			}

            VsjSysConfig sysKdConfig = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.SYS_KD_API, platformCode);
			if(null != sysKdConfig && StringUtil.isNoEmptyStr(sysKdConfig.getValue())){
				int options = Integer.parseInt(sysKdConfig.getValue());
                results.add(sysKdConfig);
				//快递api为快递鸟
				if(ExpressAPIEnum.KDN.getCode() == options){
					List<VsjSysConfig> kdnParamResult = selectByKeys(SysConfigConstants.KDN_PARAM_GROUP, platformCode);
					if(!StringUtil.isEmptyList(results)){
						results.addAll(kdnParamResult);
					}else{
						logger.info("快递查询配置为快递鸟,但未查询到快递鸟API对接配置...platformCode={}",platformCode);
						return null;
					}
				}
			}else{
				logger.info("系统未配置快递查询api...platformCode={}",platformCode);
				return null;
			}
		}
		return results;
	}

	@Override
	public List<VsjSysConfig> selectByKeys(String[] keys ,Integer platformCode){
		List<VsjSysConfig> results = new ArrayList<>();
		VsjSysConfig vsjSysConfig = null;
		for(String key :keys){
			vsjSysConfig = vsjSysConfigDAO.selectByConfigName(key, platformCode);
			if(null != vsjSysConfig){
				results.add(vsjSysConfig);
			}
		}
		return results;
	}


}
