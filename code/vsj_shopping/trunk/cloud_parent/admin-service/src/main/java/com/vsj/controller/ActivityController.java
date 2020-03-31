package com.vsj.controller;

import com.vsj.common.BaseController;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.Activity;
import com.vsj.common.model.request.ActivitySpecs;
import com.vsj.common.model.request.QueryStat;
import com.vsj.model.response.ActivityResponse;
import com.vsj.service.ActivityService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname ActivityController
 * @Description TODO
 * @Date 2019/7/29 14:24
 * @Created by zy
 */
@RestController
@RequestMapping(value="api/v1/activity/",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin
public class ActivityController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ActivityService activityService;
    /**
     * @Description 查询活动列表
     * @Author zy
     * @Date   2019/7/29 14:34
     * @Param  [queryStat]
     * type:活动类型 0=团购  1=秒杀
     * title:活动名
     * productName:商品名
     * status:状态 0=未开始  1=进行中  2=已结束
     * startTime：活动时间
     * endTime：活动时间
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "查询活动列表", notes = "查询活动列表", httpMethod = "POST")
    @RequestMapping("getActivityList")
    public  Object getActivityList(@RequestBody QueryStat queryStat){
        if(queryStat.getType()==null){
            return BaseResponse.fail("参数错误");
        }
        if (null == getPlatformCode()) {
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("查询活动列表getOrderList入参:"+queryStat);
        Object pageInfo = activityService.getActivityList(queryStat);
        logger.info("查询活动列表getOrderList出参:"+pageInfo);
        return pageInfo;
    }
    /**
     * @Description
     * @Author zy
     * @Date   2019/7/29 16:38
     * @Param  [queryStat]
     * id:活动ID
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "查询活动详情", notes = "查询活动详情", httpMethod = "POST")
    @RequestMapping("getActivityById")
    public  Object getActivityById(@RequestBody QueryStat queryStat) {

        logger.info("查询活动详情getActivityById入参:"+queryStat.getId());
        if(queryStat.getId() == null){
            return BaseResponse.fail("参数错误");
        }
        if (null == getPlatformCode()) {
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        queryStat.setPlatformCode(getPlatformCode());
        Object pageInfo = activityService.getActivityById(queryStat);
        logger.info("查询活动详情getActivityById出参:"+pageInfo);
        return pageInfo;
    }

    /**
     * @Description 查询商品规格集合
     * @Author zy
     * @Date   2019/7/29 18:07
     * @Param  [queryStat]
     * id:商品ID
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "查询商品规格列表", notes = "查询商品规格列表", httpMethod = "POST")
    @RequestMapping("getSpecsList")
    public  Object getSpecsList(@RequestBody QueryStat queryStat) {

        if (null == getPlatformCode()) {
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("查询商品规格列表getSpecsList入参:"+queryStat.getId());
        if(queryStat.getId() == null){
            return BaseResponse.fail("参数错误");
        }
        Object pageInfo = activityService.getSpecsList(queryStat);
        logger.info("查询商品规格列表getSpecsList出参:"+pageInfo);
        return pageInfo;
    }
    /**
     * @Description 添加团购活动
     * @Author zy
     * @Date   2019/7/29 19:32
     * @Param  [activityResponse]
     * productId:商品ID
     * activityName:活动名
     * startTime:开始时间
     * endTime:结束时间
     * productName:商品名
     * specsId:规格ID
     * attrJson:规格名
     * productStock:总库存
     * productNumber:活动数量
     * quotaNumber:限购数量
     * groupNumber:成团人数
     * activityPrice:活动价格
     * activityType:类型 0=团购  1=秒杀
     * sysUserId:操作人ID
     * nickName:操作人昵称
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value="添加活动接口",notes = "添加活动接口",httpMethod = "POST")
    @RequestMapping("insertActivity")
    public Object insertActivity(@RequestBody Activity activity){
        if (null == getPlatformCode()) {
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        activity.setPlatformCode(getPlatformCode());
        logger.info("添加活动属性activityResponse入参："+activity);
        return activityService.insertActivity(activity);
    }
    @RequestMapping("updateActivity")
    public BaseResponseParam updateActivity(@RequestBody QueryStat queryStat){
        if (null == getPlatformCode()) {
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        logger.info("修改活动信息入参:" + queryStat);
        queryStat.setPlatformCode(getPlatformCode());
        long startTime = System.currentTimeMillis();
        BaseResponseParam responseParam=activityService.updateActivity(queryStat);
        logger.info("修改活动请求完成,返回参数={},耗时={}", responseParam, (System.currentTimeMillis() - startTime));
        return responseParam;
    }
    /**
     * @Description 修改活动接口
     * @Author zy
     * @Date   2019/8/22 11:51
     * @Param  [activity]
     * @Return      com.vsj.common.model.BaseResponseParam
     * @Exception
     *
     */
    @RequestMapping("updateActivityAll")
    public BaseResponseParam updateActivityAll(@RequestBody Activity activity){
        if(activity.getActivityType()==null){
            return BaseResponseParam.fail("参数错误");
        }
        if (null == getPlatformCode()) {
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        activity.setPlatformCode(getPlatformCode());
        logger.info("修改活动信息入参:" + activity);
        long startTime = System.currentTimeMillis();
        BaseResponseParam responseParam=activityService.updateActivityAll(activity);
        logger.info("修改活动请求完成,返回参数={},耗时={}", responseParam, (System.currentTimeMillis() - startTime));
        return responseParam;
    }

    /**
     * @Description  修改活动商品规格信息接口
     * @Author zy
     * @Date   2019/8/22 10:48
     * @Param  [activity]
     * activityType 0=团购  1=秒杀
     * @Return      com.vsj.common.model.BaseResponseParam
     * @Exception
     *
     */
    @RequestMapping("updateActivitySpecs")
    public BaseResponseParam updateActivitySpecs(@RequestBody ActivitySpecs activitySpecs){
        if(activitySpecs.getActivityType()==null){
            return BaseResponseParam.fail("参数错误");
        }
        if (null == getPlatformCode()) {
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        activitySpecs.setPlatformCode(getPlatformCode());
        logger.info("修改活动商品规格信息入参:" + activitySpecs);
        long startTime = System.currentTimeMillis();
        BaseResponseParam responseParam=activityService.updateActivitySpecs(activitySpecs);
        logger.info("修改活动商品规格信息请求完成,返回参数={},耗时={}", responseParam, (System.currentTimeMillis() - startTime));
        return responseParam;
    }
    /**
     * @Description 删除活动商品规格信息
     * @Author zy
     * @Date   2019/8/22 11:46
     * @Param  [activitySpecs]
     * activityType 0=团购  1=秒杀
     * @Return      com.vsj.common.model.BaseResponseParam
     * @Exception
     *
     */
    @RequestMapping("delActivitySpecs")
    public BaseResponseParam delActivitySpecs(@RequestBody ActivitySpecs activitySpecs){

        if(activitySpecs.getActivityType()==null){
            return BaseResponseParam.fail("参数错误");
        }
        if (null == getPlatformCode()) {
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        activitySpecs.setPlatformCode(getPlatformCode());
        logger.info("删除活动商品规格信息入参:" + activitySpecs);
        long startTime = System.currentTimeMillis();
        BaseResponseParam responseParam=activityService.delActivitySpecs(activitySpecs);
        logger.info("删除活动商品规格信息请求完成,返回参数={},耗时={}", responseParam, (System.currentTimeMillis() - startTime));
        return responseParam;
    }

    /**
     * @Description 删除活动信息
     * @Author zy
     * @Date   2019/8/22 17:29
     * @Param  [Activity]
     * id : 活动ID
     * activityType 0=团购  1=秒杀
     * @Return      com.vsj.common.model.BaseResponseParam
     * @Exception
     *
     */
    @RequestMapping("delActivity")
    public BaseResponseParam delActivity(@RequestBody Activity Activity){

        if(Activity.getActivityType()==null){
            return BaseResponseParam.fail("参数错误");
        }
        if (null == getPlatformCode()) {
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        Activity.setPlatformCode(getPlatformCode());
        logger.info("删除活动信息入参:" + Activity);
        long startTime = System.currentTimeMillis();
        BaseResponseParam responseParam=activityService.delActivity(Activity);
        logger.info("删除活动信息请求完成,返回参数={},耗时={}", responseParam, (System.currentTimeMillis() - startTime));
        return responseParam;
    }
}
