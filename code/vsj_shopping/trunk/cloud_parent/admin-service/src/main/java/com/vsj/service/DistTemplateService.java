package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.VsjDistTemplateRequestList;
import com.vsj.common.model.request.VsjSysDistTemplateRequest;
import com.vsj.model.response.ParentDist;

/**
 * @Classname DistTemplateService
 * @Description 配送模板接口
 * @Date 2019/7/26 9:44
 * @Created by wangzx
 */
public interface DistTemplateService {

    BaseResponseParam insertDistTemplate(VsjSysDistTemplateRequest vsjDistTemplateRequest, Integer platformCode);

    BaseResponseParam getDistTemplateList(QueryStat queryStat);

    BaseResponseParam getDistTemplateDetail(QueryStat queryStat);

    BaseResponseParam updateDistTemplate(VsjSysDistTemplateRequest vsjSysDistTemplateRequest,Integer platformCode);

    BaseResponseParam deleteDistTemplate(QueryStat queryStat);

    BaseResponseParam updateDistDefault(QueryStat queryStat);

    ParentDist getCityDistConf(QueryStat queryStat);
}
