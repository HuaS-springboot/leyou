package com.vsj.material.controller;

import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.material.model.MaterialConversion;
import com.vsj.material.model.request.MaterialConversionRequest;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.service.MaterialConversionService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname MaterialConversionController
 * @Description 兑换码相关接口
 * @Date 2019/8/16 9:51
 * @Created by wangzx
 */
@CrossOrigin
@RestController
@RequestMapping(value = "api/v1/admin/conversion",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MaterialConversionController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MaterialConversionService materialConversionService;

    /**
     * @Description 批量添加兑换码
     * @Author  wangzx
     * @Date   2019/8/16 11:05
     * @Param  [materialConversionRequest]
     * levelId:兑换该兑换码提升到的会员等级
     * effectiveTime：兑换码生效时间
     * invalidTime：兑换码失效时间
     * levelDay：兑换码兑换成功后该会员的保存天数
     * count：生成兑换码的数量
     * @Return  com.vsj.common.model.BaseResponse
     * @Exception
     */
    @ApiOperation(value = "添加兑换码", notes = "添加兑换码", httpMethod = "POST")
    @PostMapping("insertMaterialConversion")
    public BaseResponseParam insertMaterialConversion( @RequestBody MaterialConversionRequest materialConversionRequest){
        logger.info("添加兑换码insertConversion入参={}",materialConversionRequest);
        BaseResponseParam baseResponseParam = materialConversionService.insertMaterialConversion(materialConversionRequest,getPlatformCode());
        logger.info("添加兑换码insertConversion出参={}",baseResponseParam);
        return baseResponseParam;
    }

    /**
     * @Description 查询兑换码列表
     * @Author  wangzx
     * @Date   2019/8/16 11:11
     * @Param  [queryStat]
     * code:兑换码
     * nickName:使用人姓名
     * telPhone:手机号
     * levelId:等级id
     * status:兑换码使用状态 0=未使用;1=已使用
     * pageNum:第几页
     * pageSize:每页几条数据
     * @Return com.vsj.common.model.BaseResponseParam
     * @Exception
     */
    @ApiOperation(value = "查询兑换码列表", notes = "查询兑换码列表", httpMethod = "POST")
    @PostMapping("/getMaterialConversionList")
    public BaseResponseParam getMaterialConversionList(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("查询兑换码列表入参={}",queryStat);
        BaseResponseParam baseResponseParam = materialConversionService.getMaterialConversionList(queryStat);
        logger.info("查询兑换码列表出参={}",baseResponseParam);
        return baseResponseParam;
    }


    /**
     * @Description 兑换会员
     * @Author  sxm
     * @Date   2019/8/16 14:00
     * @Param  [queryStat]
     * code:兑换码
     * @Return java.lang.Object
     * @Exception
     */
    @ApiOperation(value = "测试兑换码", notes = "测试兑换码", httpMethod = "POST")
    @PostMapping("getConversionByCode")
    public Object getConversionByCode(QueryStat queryStat){
        logger.info("测试兑换码code入参",queryStat);
        queryStat.setPlatformCode(getPlatformCode());
        Object obj = materialConversionService.getConversionByCode(queryStat);
        logger.info("测试兑换码code出参",obj);
        return obj;
    }


    /**
     * @Description 批量删除兑换码
     * @Author  wangzx
     * @Date   2019/8/16 13:42
     * @Param  [queryStat]
     * ids:兑换码id拼接 如:1,2,3
     * @Return com.vsj.common.model.BaseResponse
     * @Exception
     */
    @ApiOperation(value = "批量删除兑换码", notes = "批量删除兑换码", httpMethod = "POST")
    @PostMapping("/deleteMaterialConversion")
    public BaseResponseParam deleteMaterialConversion(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("批量删除兑换码deleteMaterialConversion入参={}",queryStat);
        BaseResponseParam baseResponseParam = materialConversionService.deleteMaterialConversion(queryStat);
        logger.info("批量删除兑换码批量删除兑换码deleteMaterialConversion入参出参={}",baseResponseParam);
        return baseResponseParam;
    }

}
