package com.vsj.controller;

import com.vsj.common.BaseController;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.model.request.BonusDetailsRequest;
import com.vsj.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname
 * @Description
 * @Date  2019/8/26 10:09
 * @Created by HuaS
 */
@CrossOrigin
@RequestMapping(value ="api/v1/member/",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class MemberController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemberService memberService;

    /**
     * @Description:会员查询
     * @Author HuaS
     * @Date   2019/8/26 10:08
     * @Param
     * id:会员id
     * platformCode:系统code码
     * @Return  java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "查询会员列表", notes = "查询会员列表", httpMethod = "POST")
    @PostMapping("getMemberList")
    public Object getMemberList(@RequestBody QueryStat queryStat) {
        logger.info("查询会员列表getMemberList入参:" + queryStat);
        queryStat.setPlatformCode(getPlatformCode());
        Object info = memberService.getMemberList(queryStat);
        logger.info("查询会员列表getMemberList出参:" + info);
        return info;
    }
    /**
     * @Description：查询用户余额详情
     * @Author HuaS
     * @Date   2019/8/26 17:48
     * @Param  userId:用户id
     * @Return     java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "查询用户余额详情", notes = "查询用户余额详情", httpMethod = "POST")
    @PostMapping("getBalanceDeTail")
    public Object getBalanceDeTail(@RequestBody QueryStat queryStat){
        logger.info("查询会员列表getMemberList入参:" + queryStat);
        if(queryStat.getUserId() == null){
            return BaseResponseParam.fail("用户id不能为空");
        }
        Object info = memberService.getBalanceDeTail(queryStat.getUserId());
        logger.info("查询会员列表getMemberList出参:" + info);
        return info;
    }

    /**
     * @Description:查询用户交易明细
     * @Author HuaS
     * @Date   2019/8/26 17:50
     * @Param  userId:用户id
     * @Return    java.lang.Object
     * @Exception
     *
     */
    @PostMapping("getTransactionDetail")
    @ApiOperation(value = "查询用户交易明细", notes = "查询用户交易明细", httpMethod = "POST")
    public Object getTransactionDetail(@RequestBody QueryStat queryStat){
        logger.info("查询用户交易明细getTransactionDetail:"+queryStat);
        if(queryStat.getUserId() == null){
            return BaseResponseParam.fail("用户id不能为空");
        }
        Object info = memberService.getTransactionDetail(queryStat.getUserId());
        logger.info("查询用户交易明细getTransactionDetail出参:"+info);
        return info;
    }
    /**
     * @Description:查询奖励金详情
     * @Author HuaS
     * @Date   2019/8/27 11:17
     * @Param  userId:用户id
     * @Return  BonusDetailsResponse
     * @Exception
     *
     */
    @PostMapping("getBonusDetails")
    @ApiOperation(value = "查询奖励金详情", notes = "查询奖励金详情", httpMethod = "POST")
    public Object getBonusDetails(@RequestBody BonusDetailsRequest bonusDetailsRequest){
        logger.info("查询奖励金详情getBonusDetails:"+bonusDetailsRequest);
        if(bonusDetailsRequest.getUserId() == null){
            return BaseResponseParam.fail("用户id不能为空");
        }
        Object info = memberService.getBonusDetails(bonusDetailsRequest.getUserId(),bonusDetailsRequest.getIsSettle());
        logger.info("查询奖励金详情getBonusDetails出参:"+info);
        return info;
    }

    /**
     * @Description:获取中心素材列表
     * @Author HuaS
     * @Date   2019/8/27 17:02
     * @Param
     * @Return   List<VsjMaterial>
     * @Exception
     *
     */
    @PostMapping("getMaterialList")
    @ApiOperation(value = "获取素材列表", notes = "获取中心素材列表", httpMethod = "POST")
    public Object getMaterialList(QueryStat queryStat){
        logger.info("查询素材列表getMaterialList入参:"+queryStat);
        Object info = memberService.getMaterialList(queryStat);
        logger.info("查询素材列表getMaterialList出参"+info);
        return info;
    }

}
