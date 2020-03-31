package com.vsj.service;

import com.vsj.common.model.request.QueryStat;

/**
 * @Classname VsjActivityService
 * @Description TODO
 * @Date 2019/7/30 10:17
 * @Created by wangzx
 */
public interface VsjActivityService {

    Object getActivityList(QueryStat queryStat);

    Object getActiveDetail(QueryStat queryStat);
}
