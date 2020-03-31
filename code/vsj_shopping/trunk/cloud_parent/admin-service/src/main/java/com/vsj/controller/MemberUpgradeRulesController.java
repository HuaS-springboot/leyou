package com.vsj.controller;

import com.alibaba.fastjson.JSON;
import com.vsj.common.BaseController;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.MemberUpgradeList;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.utils.StringUtil;
import com.vsj.service.IMemberUpgradeRulesService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "api/v1/memeber/upgrade", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin
public class MemberUpgradeRulesController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IMemberUpgradeRulesService memberUpgradeRulesServiceImpl;

    @ApiOperation(value = "查询会员升级条件", notes = "查询会员升级条件", httpMethod = "POST")
    @PostMapping("query")
    public BaseResponseParam findUpgradeRules(@RequestBody QueryStat queryStat) {
        if(null == queryStat || null == queryStat.getLevelId()){
            return BaseResponseParam.fail();
        }
        logger.info("开始查询会员升级条件,levelId={}",queryStat.getLevelId());
        BaseResponseParam result = memberUpgradeRulesServiceImpl.findMemberUpgradeRules(queryStat.getLevelId(),getPlatformCode());
        logger.info("查询会员升级条件出参={}", result);
        return result;
    }

    @ApiOperation(value = "修改会员升级条件", notes = "修改会员升级条件", httpMethod = "POST")
    @PostMapping("edit")
    public BaseResponseParam editUpgradeRules(@RequestBody MemberUpgradeList memberUpgradeList){
        logger.info("开始修改会员升级条件,入参={}", JSON.toJSONString(memberUpgradeList));
        if(memberUpgradeList == null || StringUtil.isEmptyList(memberUpgradeList.getMemberUpgradeList())){
            return BaseResponseParam.fail("传入待修改参数为空...");
        }
        BaseResponseParam result = memberUpgradeRulesServiceImpl.editUpgradeRules(memberUpgradeList.getMemberUpgradeList(),getPlatformCode());
        logger.info("修改会员升级条件出参={}", result);
        return result;
    }

    @ApiOperation(value = "删除会员升级条件", notes = "删除会员升级条件", httpMethod = "POST")
    @PostMapping("del")
    public BaseResponseParam delUpgradeRules(@RequestBody QueryStat queryStat) {
        logger.info("开始删除会员升级条件,入参={}", JSON.toJSONString(queryStat.getId()));
        if(null == queryStat.getId()){
            return BaseResponseParam.fail("传入id为空...");
        }
        BaseResponseParam result = memberUpgradeRulesServiceImpl.delByPrimaryKey(queryStat.getId(),getPlatformCode());
        logger.info("删除会员升级条件出参={}", result);
        return result;
    }

}
