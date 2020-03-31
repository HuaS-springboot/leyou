package com.vsj.material.controller;

import com.vsj.material.model.request.QueryStat;
import com.vsj.material.model.request.VsjMaterialRegisterRequest;
import com.vsj.material.service.VsjMaterialRegisterService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.SelectProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date :2019/8/15 18:30
 * @Describe: 用户注册相关接口
 * @Classname VsjMaterialController
 * @Created by sxm
 */
@RestController
@RequestMapping(value = "/api/v1/phone/register", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin
public class MaterialRegisterController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private VsjMaterialRegisterService vsjMaterialRegisterService;


    /**
     * @Description 注册用户信息
     * @Author  sxm
     * @Date 2019/8/15 16:31
     * @Return     java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "注册用户", notes = "注册用户", httpMethod = "POST")
    @PostMapping("/insertRegister")
    public Object insertRegister(VsjMaterialRegisterRequest vsjMaterialRegisterRequest){
        logger.info("注册用户入参",vsjMaterialRegisterRequest);
        Object obj=vsjMaterialRegisterService.insertRegister(vsjMaterialRegisterRequest,getPlatformCode());
        logger.info("测试完毕出参",obj);
        return obj;
    }

    /**
     * @Description 查看个人信息
     * @Author  sxm
     * @Date 2019/8/16 10:10
     * @Return     java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "测试用户", notes = "测试用户", httpMethod = "POST")
    @PostMapping("/getRegisterById")
    public Object getRegisterById(QueryStat queryStat){
        logger.info("测试用户id入参",queryStat);
        queryStat.setPlatformCode(getPlatformCode());
        Object obj = vsjMaterialRegisterService.getRegister(queryStat);
        logger.info("测试完成,出参",obj);
        return obj;
    }

}
