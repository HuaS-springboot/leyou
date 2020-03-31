package com.vsj.material.controller;


import com.vsj.material.model.VsjMaterialRegister;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.model.request.SysConfigList;
import com.vsj.material.service.SystemConfigService;
import com.vsj.material.service.VsjMaterialCategoryService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname SystemController
 * @Description 系统配置controller
 * @Date 2019/8/13 10:24
 * @Created by wangzx
 */
@RestController
@RequestMapping(value = "api/v1/admin/system", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin
public class SystemController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private VsjMaterialCategoryService vsjMaterialCategoryService;


    /**
     * @Description 查询系统配置列表
     * @Author  wangzx
     * @Date   2019/8/13 10:32
     * @Param  [queryStat]
     * @Return  java.lang.Object
     * @Exception
     */
    @ApiOperation(value = "查询系统配置", notes = "查询系统配置", httpMethod = "POST")
    @PostMapping("/getSysConfigList")
    public Object getSysConfigList(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("查询系统配置入参={}",queryStat);
        Object obj = systemConfigService.getSysConfigList(queryStat);
        logger.info("查询系统配置出参={}",obj);
        return obj;
    }

    /**
     * 修改系统配置
     * @param sysConfigList
     * @return
     * @Data  2019/8/21 11:00
     * @modifier wd
     */
    @ApiOperation(value = "修改系统配置", notes = "修改系统配置", httpMethod = "POST")
    @PostMapping("/updateSysConfig")
    public Object updateSysConfig(@RequestBody SysConfigList sysConfigList){
        logger.info("修改系统配置入参={}",sysConfigList);
        Object obj = systemConfigService.updateSysConfig(sysConfigList,getPlatformCode());
        logger.info("修改系统配置出参={}",obj);
        return obj;
    }

    /**
     * @Description 查询一二三级分类
     * @Author  wangzx
     * @Date   2019/8/13 16:55
     * @Return java.lang.Object
     * @Exception
     */
    @ApiOperation(value = "查询一二三级分类", notes = "查询一二三级分类", httpMethod = "POST")
    @PostMapping("/getMaterialCategory")
    public Object getMaterialCategory(){
        logger.info("查询一二三级分类入参={}",getPlatformCode());
        Object obj = vsjMaterialCategoryService.getMaterialCategory(getPlatformCode());
        logger.info("查询一二三级分类出参={}",obj);
        return obj;
    }

    /**
     * @Description 手机绑定发送验证码
     * @Author  sxm
     * @Date   2019/8/15 16:00
     * @Return java.lang.Object
     * @Exception
     */
    @ApiOperation(value = "绑定手机", notes = "绑定手机", httpMethod = "POST")
    @PostMapping("/bindPhone")
    public Object bindPhone(QueryStat querySet){
        logger.info("绑定手机入参",querySet);
        querySet.setPlatformCode(getPlatformCode());
        String code = (String)systemConfigService.bindPhone(querySet);
        logger.info("测试出参",code);
       return code;
    }



}
