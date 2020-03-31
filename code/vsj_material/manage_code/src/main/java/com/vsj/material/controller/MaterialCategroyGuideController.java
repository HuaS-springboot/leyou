package com.vsj.material.controller;

import com.vsj.material.model.MaterialCategroyGuide;
import com.vsj.material.model.request.MaterialCategroyGuideRequest;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.service.MaterialCategroyGuideService;
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
 * @Date :2019/8/16 18:00
 * @Describe: 用户注册相关接口
 * @Classname MaterialCategroyGuideController
 * @Created by sxm
 */
@CrossOrigin
@RestController
@RequestMapping(value = "api/v1/admin/materialCategroyGuide",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MaterialCategroyGuideController extends BaseController {

    @Autowired
    private MaterialCategroyGuideService materialCategroyGuideService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @Description 分类导航信息查询
     * @Author  sxm
     * @Date 2019/8/16 18:31
     * @Return     java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "分类导航查询", notes = "分类导航查询", httpMethod = "POST")
    @PostMapping("/getMaterialCategroyGuide")
    public Object getMaterialCategroyGuide(){
        logger.info("分类导航查询测试入参");
        QueryStat queryStat = new QueryStat();
        queryStat.setPlatformCode(getPlatformCode());
        Object obj = materialCategroyGuideService.getMaterialCategroyGuide(queryStat);
        logger.info("分类导航查询测试出参",obj);
        return obj;
    }

    /**
     * @Description 分类导航信息删除通过ids
     * @Author  sxm
     * @Date 2019/8/16 18:52
     * @Return     java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "分类删除", notes = "分类删除", httpMethod = "POST")
    @PostMapping("/deleteMaterialCategroyGuide")
    public Object deleteMaterialCategroyGuide(QueryStat queryStat){
        logger.info("分类删除测试入参",queryStat);
        queryStat.setPlatformCode(getPlatformCode());
        Object obj = materialCategroyGuideService.deleteMaterialCategroyGuide(queryStat);
        logger.info("分类删除导航出参",obj);
        return obj;
    }

    /**
     * @Description 分类导航信息修改
     * @Author  sxm
     * @Date 2019/8/16 19:21
     * @Return     java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "分类导航修改", notes = "分类导航修改", httpMethod = "POST")
    @PostMapping("/updateMaterialCategroyGuide")
    public Object updateMaterialCategroyGuide(MaterialCategroyGuide materialCategroyGuide){
        logger.info("分类导航修改测试入参",materialCategroyGuide);
        materialCategroyGuide.setPlatformCode(getPlatformCode());
        Object obj = materialCategroyGuideService.updateMaterialCategroyGuide(materialCategroyGuide);
        logger.info("分类导航修改测试出参",obj);
        return obj;
    }

    /**
     * @Description 分类导航信息添加
     * @Author  sxm
     * @Date 2019/8/16 19:43
     * @Return     java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "新增分类导航", notes = "新增分类导航", httpMethod = "POST")
    @PostMapping("/insertMaterialCategroyGuide")
    public Object insertMaterialCategroyGuide(MaterialCategroyGuideRequest materialCategroyGuideRequest){
        logger.info("分类导航新增测试入参",materialCategroyGuideRequest);
        Object obj = materialCategroyGuideService.insertMaterialCategroyGuide(materialCategroyGuideRequest,getPlatformCode());
        logger.info("分类导航新增测试出参",obj);
        return obj;
    }

}
