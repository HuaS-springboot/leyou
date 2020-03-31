package com.vsj.dao;

import com.vsj.mapper.ActivityMapper;
import com.vsj.model.VsjActivity;
import com.vsj.model.VsjActivityGroup;
import com.vsj.model.VsjActivitySeckill;
import com.vsj.model.VsjProductSpecs;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.ActivityResponse;
import com.vsj.model.response.ActivitySpecsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Classname ActivityDAO
 * @Description TODO
 * @Date 2019/7/29 14:53
 * @Created by zy
 */
@Component
public class ActivityDAO {

    @Autowired
    private ActivityMapper activityMapper;

    /**
     * 查询活动列表
     *
     * @Description
     * @Author zy
     * @Date 2019/7/29 19:12
     * @Param
     * @Return
     * @Exception
     */
    public List<VsjActivity> getActivityList(BaseQueryStat queryStat) {
        return activityMapper.getActivityList(queryStat);
    }

    /**
     * 查询活动详情信息
     *
     * @Description
     * @Author zy
     * @Date 2019/7/29 19:11
     * @Param
     * @Return
     * @Exception
     */
    public ActivityResponse getActivityById(BaseQueryStat queryStat) {
        return activityMapper.getActivityById(queryStat);
    }

    /**
     * @Author: HuaS
     * @Date :2019/7/30 19:59
     * @Describe:查询秒杀详情
     */
    //===================================================================
    public ActivityResponse getActivitySeckillById(BaseQueryStat queryStat) {
        return activityMapper.getActivitySeckillById(queryStat);
    }

    /**
     * @Description 根据商品ID查询规格集合
     * @Author zy
     * @Date 2019/7/29 19:11
     * @Param
     * @Return
     * @Exception
     */
    public List<VsjProductSpecs> getSpecsList(BaseQueryStat queryStat) {
        return activityMapper.getSpecsList(queryStat);
    }

    public int insertActivity(ActivityResponse activityResponse) {
        return activityMapper.insertActivity(activityResponse);
    }

    public int insertActivityGroup(List<VsjActivityGroup> vsjActivityGroup) {
        return activityMapper.insertActivityGroup(vsjActivityGroup);
    }

    public int updateActivity(BaseQueryStat baseQueryStat) {
        return activityMapper.updateActivity(baseQueryStat);
    }

    public int insertActivitySeckills(List<VsjActivitySeckill> vsjActivitySeckills) {
        return activityMapper.insertActivitySeckills(vsjActivitySeckills);
    }

    public int updateActivityAll(ActivityResponse activityResponse) {
        return activityMapper.updateActivityAll(activityResponse);
    }

    public int updateActivityGroup(ActivitySpecsResponse activitySpecsResponse) {
        return activityMapper.updateActivityGroup(activitySpecsResponse);
    }

    public int updateActivitySeckills(ActivitySpecsResponse activitySpecsResponse) {
        return activityMapper.updateActivitySeckills(activitySpecsResponse);
    }

    public ActivityResponse getProductId(ActivityResponse activityResponse) {
        return activityMapper.getProductId(activityResponse);
    }

    public int delActivityGroupById(ActivityResponse activityResponse) {
        return activityMapper.delActivityGroupById(activityResponse);
    }

    public int delActivitySeckillsById(ActivityResponse activityResponses) {
        return activityMapper.delActivitySeckillsById(activityResponses);
    }

    public int delActivityGroup(ActivitySpecsResponse activitySpecsResponse) {
        return activityMapper.delActivityGroup(activitySpecsResponse);
    }

    public int delActivitySeckills(ActivitySpecsResponse activitySpecsResponse) {
        return activityMapper.delActivitySeckills(activitySpecsResponse);
    }

    public int delActivity(ActivityResponse activityResponses) {
        return activityMapper.delActivity(activityResponses);
    }
    /**
     * @Author: HuaS
     * @Date :2019/7/30 18:44
     * @Describe:查询秒杀活动
     */
//    public List<ActivityResponse> getActivitySeckill(){
//        return activityMapper.getActivitySeckill();
//    }

}
