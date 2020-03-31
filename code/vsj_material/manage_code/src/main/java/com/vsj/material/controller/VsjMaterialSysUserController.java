package com.vsj.material.controller;

import com.vsj.common.model.BaseResponse;
import com.vsj.common.utils.StringUtil;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.service.VsjMaterialSysUserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class VsjMaterialSysUserController extends BaseController{

    @Autowired
    private VsjMaterialSysUserService vsjMaterialSysUserService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @ApiOperation(value = "系统用户登录", notes = "系统用户登录", httpMethod = "POST")
    @PostMapping(value = "loginSysUser")
    public Object loginSysUser(@RequestBody QueryStat querySet){
        logger.info("系统用户登陆入参",querySet);
        System.out.print(querySet.getPswd()+"=========="+querySet.getUserName());
        if(StringUtil.isEmptyStr(querySet.getPswd())|| StringUtil.isEmptyStr(querySet.getUserName())){
            return BaseResponse.fail("用户名或密码为空");
        }
        querySet.setPlatformCode(getPlatformCode());
        Object obj = vsjMaterialSysUserService.loginSysUser(querySet);
        logger.info("用户登陆出参",obj);
        return obj;
    }

}
