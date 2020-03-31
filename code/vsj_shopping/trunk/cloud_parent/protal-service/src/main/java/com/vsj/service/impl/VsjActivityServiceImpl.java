package com.vsj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.dao.ActivityDAO;
import com.vsj.model.VsjActivity;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.ActivityResponse;
import com.vsj.service.VsjActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname VsjActivityServiceImpl
 * @Description TODO
 * @Date 2019/7/30 10:17
 * @Created by wangzx
 */
@Service
public class VsjActivityServiceImpl implements VsjActivityService {

    @Autowired
    private ActivityDAO activityDAO;

    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> abstractObjectConverter;
    @Override
    public Object getActivityList(QueryStat queryStat) {
        PageHelper.startPage(queryStat.getPageNum(),queryStat.getPageSize());
        BaseQueryStat baseQueryStat=abstractObjectConverter.convert(queryStat,BaseQueryStat.class);
        List<VsjActivity> activityList = activityDAO.getActivityList(baseQueryStat);
        PageInfo<VsjActivity> pageInfo = new PageInfo<>(activityList);
        return BaseResponseParam.success(pageInfo);
    }

    @Override
    public Object getActiveDetail(QueryStat queryStat) {
        if(queryStat.getId() == null){
            return BaseResponse.fail("参数错误");
        }
        BaseQueryStat baseQueryStat=abstractObjectConverter.convert(queryStat,BaseQueryStat.class);
        ActivityResponse activityResponse = activityDAO.getActivityById(baseQueryStat);
        return BaseResponseParam.success(activityResponse);
    }
}
