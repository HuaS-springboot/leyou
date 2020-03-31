package com.vsj.service;

import java.util.Map;

import com.vsj.common.model.BaseResponseParam;

public interface IDictionaryService {
	BaseResponseParam<Map<String,Map<String,String>>> selectSysDictionary();
}
