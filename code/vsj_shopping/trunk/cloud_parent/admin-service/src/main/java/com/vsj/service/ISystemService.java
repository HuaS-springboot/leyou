package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.SysConfig;
import com.vsj.model.VsjSysConfig;

import java.util.List;

/**
 * 
 * @ClassName: ISystemService
 * @Description: 系统配置接口
 * @author: mario 
 * @date: 2019年7月31日 下午5:48:07
 * @copyright: 青岛微视角文化传媒有限公司
 */
@SuppressWarnings("rawtypes")
public interface ISystemService {

	/**
	 * 
	 * @Title: updateSysConfig
	 * @Description: 保存系统配置信息
	 * @param vsjSysConfigList
	 * @param platformCode
	 * @return
	 * @author mario
	 * @return: BaseResponseParam
	 */
	BaseResponseParam updateSysConfig(List<SysConfig> vsjSysConfigList,Integer platformCode);
	
	/**
	 * 
	 * @Title: selectSysConfigList
	 * @Description: 查询系统配置列表
	 * @param type - 参考SysConfigClassifyConstants
	 * @param platformCode
	 * @return
	 * @author mario
	 * @return: BaseResponseParam
	 */
	BaseResponseParam selectSysConfigList(Byte type, Integer platformCode);
	
	/**
	 * 
	 * @Title: selectByKeys
	 * @Description: 查询一组系统配置
	 * @param keys
	 * @param platformCode
	 * @return
	 * @author mario
	 * @return: List<VsjSysConfig>
	 */
	List<VsjSysConfig> selectByKeys(String[] keys ,Integer platformCode);
}
