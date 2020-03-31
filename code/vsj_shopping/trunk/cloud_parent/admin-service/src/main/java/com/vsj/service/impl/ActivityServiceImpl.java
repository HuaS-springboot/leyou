package com.vsj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.BaseController;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.Activity;
import com.vsj.common.model.request.ActivitySpecs;
import com.vsj.common.model.request.QueryStat;
import com.vsj.dao.ActivityDAO;
import com.vsj.model.VsjActivity;
import com.vsj.model.VsjActivitySeckill;
import com.vsj.model.VsjMaterial;
import com.vsj.model.VsjProductSpecs;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.ActivityResponse;
import com.vsj.model.response.ActivitySpecsResponse;
import com.vsj.service.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Classname ActivityServiceImpl
 * @Description TODO
 * @Date 2019/7/29 14:38
 * @Created by zy
 */
@Service
public class ActivityServiceImpl extends BaseController implements ActivityService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ActivityDAO activityDAO;
    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> queryStatConvert;
    @Autowired
    private AbstractObjectConverter<Activity, ActivityResponse> convert;
    @Autowired
    private AbstractObjectConverter<ActivitySpecs, ActivitySpecsResponse> ActivitySpecsConvert;
    /**
     * @Description 查询活动管理列表
     * @Author zy
     * @Date   2019/7/29 14:40
     * @Param  [queryStat]
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @Override
    public Object getActivityList(QueryStat queryStat) {
        PageHelper.startPage(queryStat.getPageNum(),queryStat.getPageSize());
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        List<VsjActivity> list = activityDAO.getActivityList(baseQueryStat);
        PageInfo<VsjActivity> pageInfo = new PageInfo<>(list);
        return BaseResponseParam.success(pageInfo);
    }
    /**查询活动详情信息接口
     * @Description
     * @Author zy
     * @Date   2019/7/29 16:40
     * @Param  [queryStat]
     * id:活动ID
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @Override
    public Object getActivityById(QueryStat queryStat) {
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        ActivityResponse activityResponse;
        if(baseQueryStat.getType()==null){
            return BaseResponseParam.fail("无效参数");
        }
        if (queryStat.getType()==0) {
             activityResponse = activityDAO.getActivityById(baseQueryStat);
        }else{
            activityResponse = activityDAO.getActivitySeckillById(baseQueryStat);
        }
        return BaseResponseParam.success(activityResponse);
    }


    /**
     * @Description 根据商品ID查询规格集合
     * @Author zy
     * @Date   2019/7/29 19:10
     * @Param  [queryStat]
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @Override
    public Object getSpecsList(QueryStat queryStat) {
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        List<VsjProductSpecs> list = activityDAO.getSpecsList(baseQueryStat);
        return  BaseResponseParam.success(list);
    }
    /**
     * @Description 添加团购活动
     * @Author zy
     * @Date   2019/7/29 19:40
     * @Param  [activityResponse]
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @Override
    public Object insertActivity(Activity activity) {
        ActivityResponse activityResponse = convert.convert(activity, ActivityResponse.class);
        if (null == activityResponse) {
            logger.debug("活动对象转换失败insertMaterial...");
            return BaseResponseParam.fail();
        }

            int a = activityDAO.insertActivity(activityResponse);
            if (a <= 0) {
                return BaseResponseParam.fail("添加活动失败");
            }
            if (activityResponse.getActivityType() == 0) {
                activityResponse.getVsjActivityGroup().stream().forEach(g -> {
                    g.setActivityId(activityResponse.getId());
                    g.setPlatformCode(activityResponse.getPlatformCode());
                });
                int count = activityDAO.insertActivityGroup(activityResponse.getVsjActivityGroup());
                if (count > 0) {
                    return BaseResponseParam.success();
                }
            } else {
                activityResponse.getVsjActivitySeckills().stream().forEach(g -> {
                    g.setActivityId(activityResponse.getId());
                    g.setPlatformCode(activityResponse.getPlatformCode());
                });
                int count = activityDAO.insertActivitySeckills(activityResponse.getVsjActivitySeckills());
                if (count > 0) {
                    return BaseResponseParam.success();
                }
            }
        return BaseResponseParam.fail("添加活动失败");
    }

    @Override
    public BaseResponseParam updateActivity(QueryStat queryStat) {
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        int count=activityDAO.updateActivity(baseQueryStat);
        if(count>0){
            return BaseResponseParam.success();
        }
        return BaseResponseParam.fail("添加活动失败");
    }

    @Override
    public BaseResponseParam updateActivityAll(Activity activity) {
        ActivityResponse activityResponses = convert.convert(activity, ActivityResponse.class);
        if (null == activityResponses) {
            logger.debug("活动对象转换失败");
            return BaseResponseParam.fail();
        }
        ActivityResponse getProductId=activityDAO.getProductId(activityResponses);
            int a = activityDAO.updateActivityAll(activityResponses);
            if (a > 0) {

            int i = activityDAO.updateActivityAll(activityResponses);
            if (i > 0) {
                if (activityResponses.getActivityType() == 0) {
                    int delCount=activityDAO.delActivityGroupById(activityResponses);
                    if (delCount>0) {
                        activityResponses.getVsjActivityGroup().stream().forEach( g -> {
                            g.setActivityId(activityResponses.getId());
                            g.setPlatformCode(activityResponses.getPlatformCode());
                        });
                        int count = activityDAO.insertActivityGroup(activityResponses.getVsjActivityGroup());
                        if (count > 0) {
                            return BaseResponseParam.success();
                        }
                    }
                } else {
                    int delCount=activityDAO.delActivitySeckillsById(activityResponses);
                    if (delCount>0) {
                        activityResponses.getVsjActivitySeckills().stream().forEach(g -> {
                            g.setActivityId(activityResponses.getId());
                            g.setPlatformCode(activityResponses.getPlatformCode());
                        });
                        int count = activityDAO.insertActivitySeckills(activityResponses.getVsjActivitySeckills());
                        if (count > 0) {
                            return BaseResponseParam.success();
                        }
                    }
                }
            }
        }
        return BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam updateActivitySpecs(ActivitySpecs activitySpecs) {
        ActivitySpecsResponse activitySpecsResponse=ActivitySpecsConvert.convert(activitySpecs, ActivitySpecsResponse.class);
        if (activitySpecsResponse.getActivityType() == 0) {
            int count=activityDAO.updateActivityGroup(activitySpecsResponse);
            if (count > 0) {
                return BaseResponseParam.success();
            }
        }else{
            int count = activityDAO.updateActivitySeckills(activitySpecsResponse);
            if (count > 0) {
                return BaseResponseParam.success();
            }
        }
        return BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam delActivitySpecs(ActivitySpecs activitySpecs) {
        ActivitySpecsResponse activitySpecsResponse=ActivitySpecsConvert.convert(activitySpecs, ActivitySpecsResponse.class);
        if (activitySpecsResponse.getActivityType() == 0) {
            int count=activityDAO.delActivityGroup(activitySpecsResponse);
            if (count > 0) {
                return BaseResponseParam.success();
            }
        }else {
            int count=activityDAO.delActivitySeckills(activitySpecsResponse);
            if (count > 0) {
                return BaseResponseParam.success();
            }
        }
        return BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam delActivity(Activity activity) {
        ActivityResponse activityResponses = convert.convert(activity, ActivityResponse.class);
        int count = activityDAO.delActivity(activityResponses);
        if (count >0){
            if (activityResponses.getActivityType() == 0) {
                int delCount=activityDAO.delActivityGroupById(activityResponses);
                if (delCount>0) {
                    return BaseResponseParam.success();
                }
            }else {
                int delCount=activityDAO.delActivitySeckillsById(activityResponses);
                if (delCount>0) {
                    return BaseResponseParam.success();
                }
            }
        }
        return BaseResponseParam.fail();
    }


}
