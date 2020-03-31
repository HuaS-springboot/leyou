package com.vsj.controller;

import com.vsj.common.BaseController;
import com.vsj.common.model.request.MaterialL;
import com.vsj.common.model.request.QueryStat;
import com.vsj.model.VsjMaterial;
import com.vsj.model.wechat.MaterialList;
import com.vsj.service.MaterialService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * @Author: HuaS
 * @Date :2019/8/8 10:00
 * @Describe:
 */
@RestController
@RequestMapping(value = "api/v1/material",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin
public class MaterialController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MaterialService materialService;

    @ApiOperation(value = "查询素材列表", notes = "查询素材列表", httpMethod = "POST")
    @PostMapping("getMaterialList")
    public Object getMaterialList(QueryStat queryStat){
        logger.info("查询素材列表getMaterialList入参:"+queryStat);
        Object info = materialService.getMaterialList(queryStat);
        logger.info("查询素材列表getMaterialList出参"+info);
        return info;
    }
    @ApiOperation(value = "插入素材列表", notes = "插入素材列表", httpMethod = "POST")
    @PostMapping("insertMaterial")
    public Object insertMaterial(@RequestBody MaterialL materialL){
        logger.info("插入素材列表insertMaterial入参:"+ materialL );
        materialL.setPlatformCode(getPlatformCode());
        Object info = materialService.insertMaterial(materialL);
        logger.info("插入素材列表insertMaterial出参"+info);
        return info;
    }

    @ApiOperation(value = "修改素材列表", notes = "修改素材列表", httpMethod = "POST")
    @PostMapping("updateMaterial")
    public Object updateMaterial(@RequestBody MaterialList materialList){
        logger.info("修改素材列表updateMaterial入参:"+materialList);
        Object obj = materialService.updateMaterial(materialList,getPlatformCode());
        logger.info("修改素材列表updateMaterial出参"+obj);
        return obj;
    }

    @ApiOperation(value = "删除素材列表", notes = "删除素材列表", httpMethod = "POST")
    @PostMapping("deleteMaterial")
    public Object deleteMaterial(@RequestBody VsjMaterial vsjMaterial){
        logger.info("删除素材列表deleteMaterial出参:"+vsjMaterial);
        Object info = materialService.deleteMaterial(vsjMaterial);
        logger.info("删除素材列表deleteMaterial入参"+info);
        return info;
    }

}
