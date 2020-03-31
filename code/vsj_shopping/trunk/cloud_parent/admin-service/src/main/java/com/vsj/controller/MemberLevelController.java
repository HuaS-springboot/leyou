package com.vsj.controller;

import com.vsj.common.BaseController;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.MemberLevel;
import com.vsj.common.model.request.QueryStat;
import com.vsj.service.MemberLevelService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName MemberLevelController
 * @Description: TODO
 * @Author mario
 * @Date 2019/8/24
 * @Version V1.0
 **/
@RestController
@RequestMapping(value = "api/v1/memeber/level", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin
public class MemberLevelController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemberLevelService memberLevelService;

    @ApiOperation(value = "查询会员等级", notes = "查询会员等级", httpMethod = "POST")
    @PostMapping("query")
    public BaseResponseParam findAllLevel() {
        logger.info("查询会员等级findAllLevel");
        BaseResponseParam result = memberLevelService.findAllLevel(getPlatformCode());
        logger.info("查询会员等级findAllLevel出参={}", result);
        return result;
    }

    @ApiOperation(value = "删除会员级别", notes = "删除会员级别", httpMethod = "POST")
    @PostMapping("delete")
    public BaseResponseParam deleteLevelById(@RequestBody QueryStat queryStat) {
        if (null == queryStat || null == queryStat.getId()) {
            return BaseResponseParam.fail("等级Id传入为空...");
        }
        logger.info("删除会员级别,Id={}", queryStat.getId());
        BaseResponseParam result = memberLevelService.deleteLevelById(queryStat.getId(), getPlatformCode());
        logger.info("删除会员级别完成,结果={}", result);
        return result;
    }

    @ApiOperation(value = "修改会员级别", notes = "修改会员级别", httpMethod = "POST")
    @PostMapping("edit")
    public BaseResponseParam editMemberLevel(@RequestBody MemberLevel memberLevel) {
        logger.info("修改会员级别editMemberLevel入参={}", memberLevel);
        memberLevel.setPlatformCode(getPlatformCode());
        BaseResponseParam result = memberLevelService.updateLevel(memberLevel);
        logger.info("修改会员级别editMemberLevel出参={}", result);
        return result;
    }

    @ApiOperation(value = "修改默认会员等级", notes = "修改默认会员等级", httpMethod = "POST")
    @PostMapping("edit-default")
    public BaseResponseParam editDefaultLevel(@RequestBody MemberLevel memberLevel) {
        logger.info("修改默认会员等级入参 ={}", memberLevel);
        if (null == memberLevel || null == memberLevel.getId()) {
            return BaseResponseParam.fail("传入的默认等级会员Id为空");
        }
        memberLevel.setPlatformCode(getPlatformCode());
        BaseResponseParam obj = memberLevelService.editDefaultLevel(memberLevel);
        logger.info("修改默认会员等级出参={}", obj);
        return obj;
    }

}
