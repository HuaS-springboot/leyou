package com.vsj.material.controller;

import com.vsj.material.model.request.QueryStat;
import com.vsj.material.service.VsjMaterialOrderService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname OrderController
 * @Description 订单相关接口
 * @Date 2019/8/15 9:04
 * @Created by wangzx
 */
@RestController
@RequestMapping(value = "api/v1/admin/order", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin
public class OrderController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private VsjMaterialOrderService vsjMaterialOrderService;


    /**
     * @Description 查询会员订单列表
     * @Author  wangzx
     * @Date   2019/8/15 9:08
     * @Param  [queryStat]
     * orderId:订单号
     * userName:用户名
     * phone:手机号
     * levelId：等级id
     * pageNum:第几页
     * pageSize:每页几条
     * @Return  java.lang.Object
     * @Exception
     */
    @ApiOperation(value = "查询会员订单", notes = "查询会员订单", httpMethod = "POST")
    @PostMapping("/getMemberOrderList")
    public Object getMemberOrderList(@RequestBody QueryStat queryStat) throws Exception{
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("查询会员订单列表入参={}",queryStat);
        Object obj = vsjMaterialOrderService.getMemberOrderList(queryStat);
        return obj;
    }
}
