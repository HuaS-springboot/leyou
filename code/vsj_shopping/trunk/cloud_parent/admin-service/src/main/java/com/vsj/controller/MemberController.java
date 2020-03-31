package com.vsj.controller;

import com.alibaba.fastjson.JSON;
import com.vsj.common.BaseController;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.MemberEditDetail;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.VsjMemberRelation;
import com.vsj.service.MemberProfitService;
import com.vsj.service.MemberRelationService;
import com.vsj.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: HuaS
 * @Date :2019/7/24 11:05
 * @Describe:会员管理接口
 */
@RestController
@RequestMapping(value = "api/v1/memeber", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin
public class MemberController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());     // extends BaseController

    @Autowired
    private MemberRelationService memberRelationService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberProfitService memberProfitService;

    @ApiOperation(value = "查询会员列表", notes = "查询会员列表", httpMethod = "POST")
    @PostMapping("list")
    public Object getMemberList(@RequestBody QueryStat queryStat) {
        logger.info("查询会员列表getMemberList入参:" + queryStat);
        queryStat.setPlatformCode(getPlatformCode());
        Object info = memberService.getMemberList(queryStat);
        logger.info("查询会员列表getMemberList出参:" + info);
        return info;
    }

    @ApiOperation(value = "获取会员信息详情", notes = "获取会员信息详情", httpMethod = "POST")
    @PostMapping("detail")
    public BaseResponseParam getMemberDetail(@RequestBody QueryStat queryStat) {
        logger.info("获取会员信息详情getMemberDetail入参={}", queryStat.getId());
        BaseResponseParam param = memberProfitService.getMemberDetail(queryStat.getId(), getPlatformCode());
        logger.info("获取会员信息详情getMemberDetail出参={}", param);
        return param;
    }

    @ApiOperation(value = "修改会员信息详情", notes = "修改会员信息详情", httpMethod = "POST")
    @PostMapping("detail/edit")
    public BaseResponseParam editMemberDetail(@RequestBody MemberEditDetail memberEditDetail) {
        logger.info("修改会员详情入参 ={}", memberEditDetail);
        if (memberEditDetail.getId() == null || memberEditDetail.getRegId() == null) {
            return BaseResponseParam.fail("传入信息有误...请刷新后重新尝试...");
        }
        memberEditDetail.setPlatformCode(getPlatformCode());
        BaseResponseParam obj = memberService.editMemberDetail(memberEditDetail);
        logger.info("修改会员详情出参={}", obj);
        return obj;
    }

    @ApiOperation(value = "会员列表导出", notes = "会员列表导出", httpMethod = "POST")
    @RequestMapping("export")
    public BaseResponseParam exportMemberListExcel(String nickName, String parentName, String telPhone, Integer levelId, String startTime, String endTime, Integer platformCode, HttpServletResponse response) {
        logger.info("开始处理会员列表导出...");
        long start = System.currentTimeMillis();
        QueryStat queryStat = new QueryStat();
        queryStat.setNickName(nickName);
        queryStat.setParentName(parentName);
        queryStat.setTelPhone(telPhone);
        queryStat.setLevelId(levelId);
        queryStat.setStartTime(startTime);
        queryStat.setEndTime(endTime);
        queryStat.setPlatformCode(platformCode);
        logger.debug("请求参数={}", JSON.toJSONString(queryStat));
        boolean flag = memberService.exportMemberListExcel(queryStat,response);
        logger.info("会员列表导出完成...耗时={}", (System.currentTimeMillis() - start));
        if(flag) {
            return BaseResponseParam.success();
        }else{
            return BaseResponseParam.fail("根据条件查询到的导出数据为空...");
        }
    }

    @ApiOperation(value = "查询会员关系", notes = "查询会员关系", httpMethod = "POST")
    @PostMapping("findMemberRelation")
    public Object findMemberRelation() {
        logger.info("查询会员关系findMemberRelation请求");
        Object vsjSysRelationSet = memberRelationService.getMemberRelation(getPlatformCode());
        logger.info("查询会员关系findMemberRelation出参:" + vsjSysRelationSet);
        return vsjSysRelationSet;
    }

    @ApiOperation(value = "修改会员关系", notes = "修改会员关系", httpMethod = "POST")
    @PostMapping("updateMemberRelation")
    public Object updateMemberRelation(@RequestBody VsjMemberRelation vsjMemberRelation) {
        logger.info("修改会员关系vsjSysRelationSet入参:" + vsjMemberRelation);
        vsjMemberRelation.setPlatformCode(getPlatformCode());
        Object obj = memberRelationService.updateMemberRelation(vsjMemberRelation);
        logger.info("修改会员关系vsjSysRelationSet出参:" + obj);
        return obj;
    }

}
