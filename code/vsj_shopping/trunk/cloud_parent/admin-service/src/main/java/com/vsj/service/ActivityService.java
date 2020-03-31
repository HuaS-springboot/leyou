package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.Activity;
import com.vsj.common.model.request.ActivitySpecs;
import com.vsj.common.model.request.QueryStat;
import com.vsj.model.response.ActivityResponse;

import java.util.Map;

/**
 * @Classname ActivityService
 * @Description TODO
 * @Date 2019/7/29 14:38
 * @Created by zy
 */
public interface ActivityService {
    /**
    * @Description 查询活动管理列表
    * @Author zy
    * @Date   2019/7/29 14:39
    * @Param
    * @Return
    * @Exception
    *
    */
    Object getActivityList(QueryStat queryStat);
    /**查询活动详情信息接口
    * @Description
    * @Author zy
    * @Date   2019/7/29 16:39
    * @Param
    * @Return
    * @Exception
    *
    */
    Object getActivityById(QueryStat queryStat);
    /**
    * @Description 询商品规格列表
    * @Author zy
    * @Date   2019/7/29 18:08
    * @Param
    * @Return
    * @Exception
    *
    */
    Object getSpecsList(QueryStat queryStat);
    /**
    * @Description 添加团购活动
    * @Author zy
    * @Date   2019/7/29 19:38
    * @Param
    * @Return
    * @Exception
    *
    */
    Object insertActivity(Activity activity);
    /**
    * @Description 修改活动状态接口
    * @Author zy
    * @Date   2019/7/30 10:19
    * @Param
    * @Return
    * @Exception
    *
    */
    BaseResponseParam updateActivity(QueryStat queryStat);

    /**
    * @Description 修改活动信息接口
    * @Author zy
    * @Date   2019/8/22 9:09
    * @Param
    * @Return
    * @Exception
    *
    */
    BaseResponseParam updateActivityAll(Activity activity);
    /**
    * @Description 修改活动商品规格信息接口
    * @Author zy
    * @Date   2019/8/22 10:49
    * @Param
    * @Return
    * @Exception
    *
    */
    BaseResponseParam updateActivitySpecs(ActivitySpecs activitySpecs);

    /**
    * @Description 删除活动商品规格
    * @Author zy
    * @Date   2019/8/22 11:05
    * @Param
    * @Return
    * @Exception
    *
    */
    BaseResponseParam delActivitySpecs(ActivitySpecs activitySpecs);

    /**
    * @Description 删除活动信息
    * @Author zy
    * @Date   2019/8/22 17:32
    * @Param
    * @Return
    * @Exception
    *
    */
    BaseResponseParam delActivity(Activity activity);
}
