package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.VsjProductRequest;
import com.vsj.common.model.request.VsjStageSchemaRequest;
import com.vsj.common.model.request.VsjStageSchemaRequestList;

/**
 * @Classname StageSchemaService
 * @Description 实现类
 * @Date 2019/7/26 17:25
 * @Created by wangzx
 */
public interface StageSchemaService {

    BaseResponseParam insertStageSchema(VsjStageSchemaRequestList vsjStageSchemaRequestList, Integer platformCode);

    BaseResponseParam updateStageSchema(VsjProductRequest vsjProductRequest, VsjStageSchemaRequest vsjStageSchemaRequest,Integer platformCode);

    BaseResponseParam deleteStageSchema(QueryStat queryStat);

    BaseResponseParam getStageSchemaList(QueryStat queryStat);
}
