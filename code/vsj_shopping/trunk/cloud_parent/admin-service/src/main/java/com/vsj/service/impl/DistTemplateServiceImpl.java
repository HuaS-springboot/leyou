package com.vsj.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.VsjDistTemplateRequestList;
import com.vsj.common.model.request.VsjSysDistTemplateRequest;
import com.vsj.common.redis.client.RedisClient;
import com.vsj.common.redis.constant.RedisKeyConstant;
import com.vsj.common.utils.StringUtil;
import com.vsj.dao.DistTemplateDAO;
import com.vsj.model.VsjSysDistTemplate;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.CityDist;
import com.vsj.model.response.DistChildren;
import com.vsj.model.response.DistTemplateResponse;
import com.vsj.model.response.ParentDist;
import com.vsj.service.DistTemplateService;
import javafx.scene.Parent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname DistTemplateServiceImpl
 * @Description TODO
 * @Date 2019/7/26 9:44
 * @Created by wangzx
 */
@Service
public class DistTemplateServiceImpl implements DistTemplateService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DistTemplateDAO distTemplateDAO;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private AbstractObjectConverter<VsjSysDistTemplateRequest, VsjSysDistTemplate> converter;
    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> queryStatConvert;

    @Override
    public BaseResponseParam insertDistTemplate(VsjSysDistTemplateRequest vsjSysDistTemplateRequest, Integer platformCode) {
        VsjSysDistTemplate vsjSysDistTemplate = converter.convert(vsjSysDistTemplateRequest, VsjSysDistTemplate.class);
        vsjSysDistTemplate.setPlatformCode(platformCode);
        int c = distTemplateDAO.getDistCount(platformCode);
        if(c == 0){
            // 如果该平台下没有模板则第一条设置为默认
            vsjSysDistTemplate.setStatus(1);
        }
        int count = distTemplateDAO.insertDistTemplate(vsjSysDistTemplate);
        if(count > 0){
            return BaseResponseParam.success();
        }
        return BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam getDistTemplateList(QueryStat queryStat) {
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat, BaseQueryStat.class);
        PageHelper.startPage(queryStat.getPageNum(),queryStat.getPageSize());
        List<VsjSysDistTemplate> distTemplateList = distTemplateDAO.getDistTemplateList(baseQueryStat);
        PageInfo<VsjSysDistTemplate> pageInfo = new PageInfo<>(distTemplateList);
        return BaseResponseParam.success(pageInfo);
    }

    @Override
    public BaseResponseParam getDistTemplateDetail(QueryStat queryStat) {
        if (queryStat.getId() == null) {
            return BaseResponseParam.fail("参数错误");
        }
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat, BaseQueryStat.class);
        VsjSysDistTemplate template = distTemplateDAO.getDistTemplateDetail(baseQueryStat);
        return BaseResponseParam.success(template);
    }

    @Override
    public BaseResponseParam updateDistTemplate(VsjSysDistTemplateRequest vsjSysDistTemplateRequest, Integer platformCode) {
        VsjSysDistTemplate vsjSysDistTemplate = converter.convert(vsjSysDistTemplateRequest, VsjSysDistTemplate.class);
        vsjSysDistTemplate.setPlatformCode(platformCode);
        if (vsjSysDistTemplate.getId() == null) {
            return BaseResponseParam.fail("参数错误");
        }
        int count = distTemplateDAO.updateDistTemplate(vsjSysDistTemplate);
        if (count > 0) {
            return BaseResponseParam.success();
        }
        return BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam deleteDistTemplate(QueryStat queryStat) {
        if (queryStat.getId() == null) {
            return BaseResponseParam.fail("参数错误");
        }
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat, BaseQueryStat.class);
        int count = distTemplateDAO.deleteDistTemplate(baseQueryStat);
        if (count > 0) {
            return BaseResponseParam.success();
        }
        return BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam updateDistDefault(QueryStat queryStat) {
        if (queryStat.getId() == null || queryStat.getOldId() == null || queryStat.getPlatformCode() == null) {
            return BaseResponseParam.fail("参数错误");
        }
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat, BaseQueryStat.class);
        // 修改新的配送模板为默认
        VsjSysDistTemplate vsjSysDistTemplate = new VsjSysDistTemplate();
        vsjSysDistTemplate.setPlatformCode(baseQueryStat.getPlatformCode());
        vsjSysDistTemplate.setId(baseQueryStat.getId());
        vsjSysDistTemplate.setStatus(1);
        distTemplateDAO.updateDistTemplate(vsjSysDistTemplate);
        // 修改之前的默认的默认模板为否
        vsjSysDistTemplate.setId(baseQueryStat.getOldId());
        vsjSysDistTemplate.setStatus(0);
        distTemplateDAO.updateDistTemplate(vsjSysDistTemplate);
        return BaseResponseParam.success();
    }

    @Override
    public ParentDist getCityDistConf(QueryStat queryStat) {
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat, BaseQueryStat.class);
        VsjSysDistTemplate distTemplateDetail = distTemplateDAO.getDistTemplateDetail(baseQueryStat);
        DistTemplateResponse distTemplateResponse = JSON.parseObject(distTemplateDetail.getConfiguration(), DistTemplateResponse.class);
        // 保存配送配置
        ParentDist parentDist = new ParentDist();

        // 查询该配置是否配置了该市的配置
        if(distTemplateResponse != null){
            List<DistChildren> childrenList = distTemplateResponse.getChildren();
            if(!childrenList.isEmpty()){
                for (DistChildren distChildren : childrenList) {
                    List<CityDist> cityCode = distChildren.getCityDist();
                    for (CityDist cityConfig : cityCode) {
                        if(cityConfig != null){
                            List<String> list = cityConfig.getCityCode();
                            if(list.contains(queryStat.getCityCode())){
                                parentDist.setFirstHeavy(distChildren.getFirstHeavy());
                                parentDist.setFirstHeavyCost(distChildren.getFirstHeavyCost());
                                parentDist.setModel(distChildren.getModel());
                                parentDist.setNextHeavy(distChildren.getNextHeavy());
                                parentDist.setNextHeavyCost(distChildren.getNextHeavyCost());
                                parentDist.setProvinceCode(cityConfig.getProvince());
                                parentDist.setName(distChildren.getName());
                                parentDist.setCityCode(queryStat.getCityCode());
                            }
                        }
                    }
                }
            }

            // 没有配置，查询通用配置
            if(parentDist.getModel() == null){
                parentDist.setFirstHeavy(distTemplateResponse.getParentDist().getFirstHeavy());
                parentDist.setFirstHeavyCost(distTemplateResponse.getParentDist().getFirstHeavyCost());
                parentDist.setModel(distTemplateResponse.getParentDist().getModel());
                parentDist.setNextHeavy(distTemplateResponse.getParentDist().getNextHeavy());
                parentDist.setNextHeavyCost(distTemplateResponse.getParentDist().getNextHeavyCost());
                parentDist.setProvinceCode(distTemplateResponse.getParentDist().getProvinceCode());
                parentDist.setName(distTemplateResponse.getParentDist().getName());
            }
        }
        logger.info("该市的配送模板配置为={}",parentDist);
        return parentDist;
    }
}
