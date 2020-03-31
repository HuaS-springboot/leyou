package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.VsjStageDistrRequest;

/**
 * @Classname VsjStageDistrService
 * @Description 分销接口
 * @Date 2019/8/14 14:05
 * @Created by wangzx
 */
public interface VsjStageDistrService {

    BaseResponseParam insertStageDistr(VsjStageDistrRequest vsjStageDistrRequest, Integer platformCode);

    BaseResponseParam getStageDistrByProductId(QueryStat queryStat);

    BaseResponseParam updateStageDistrById(VsjStageDistrRequest vsjStageDistrRequest,Integer platformCode);

    BaseResponseParam deleteStageDistrById(QueryStat queryStat);
}
