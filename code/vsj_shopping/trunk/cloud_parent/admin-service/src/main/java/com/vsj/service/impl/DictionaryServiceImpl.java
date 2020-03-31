package com.vsj.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.utils.StringUtil;
import com.vsj.service.IDictionaryService;
import com.vsj.dao.VsjSysDictionaryDAO;

@Service
public class DictionaryServiceImpl implements IDictionaryService {
	
	@Autowired
	private VsjSysDictionaryDAO vsjSysDictionaryDAO;
	
	@Override
	public BaseResponseParam<Map<String,Map<String,String>>> selectSysDictionary() {
		Map<String,Map<String,String>> resultMap = vsjSysDictionaryDAO.selectSysDictionary();
		if(StringUtil.isEmptyMap(resultMap)){
			return BaseResponseParam.fail("查询系统数据字典失败...");
		}else{
			return BaseResponseParam.success(resultMap);
		}
	}

}
