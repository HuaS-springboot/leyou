package com.vsj.controller;

import com.vsj.common.BaseController;
import com.vsj.model.VsjSaasSystem;
import com.vsj.service.SaasSystemService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname SaasSystemController
 * @Description Saas系统设置
 * @Date 2019/7/31 14:53
 * @Created by wangzx
 */
@RestController
@RequestMapping(value = "api/v1/saas/system/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin
public class SaasSystemController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SaasSystemService saasSystemService;


    /**
     * @Description saas系统管理--添加
     * @Author  wangzx
     * @Date   2019/7/31 15:22
     * @Param  [vsjSaasSystem]
     * @Return java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "新增saas系统", notes = "新增saas系统", httpMethod = "POST")
    @PostMapping("insertSaasSystem")
    public Object insertSaasSystem(VsjSaasSystem vsjSaasSystem){
        vsjSaasSystem.setPlatformCode(getPlatformCode());
        logger.info("新增saas系统入参={}",vsjSaasSystem);
        Object obj = saasSystemService.insertSaasSystem(vsjSaasSystem);
        logger.info("新增saas系统出参={}",obj);
        return obj;
    }
}
