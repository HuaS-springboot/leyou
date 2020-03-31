package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.VsjProductExtensionRequestList;

/**
 * @Classname ProductExtensionService
 * @Description TODO
 * @Date 2019/7/24 18:31
 * @Created by wangzx
 */
public interface ProductExtensionService {

    BaseResponseParam insertProductExtension(VsjProductExtensionRequestList vsjProductExtensionRequestList, Integer platformCode);

    BaseResponseParam getExtensionList(QueryStat queryStat);

    BaseResponseParam updateExtension(VsjProductExtensionRequestList vsjProductExtensionRequestList,Integer platformCode);

    BaseResponseParam deleteExtension(QueryStat queryStat);
}
