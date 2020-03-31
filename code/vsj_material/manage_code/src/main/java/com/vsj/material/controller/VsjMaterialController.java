package com.vsj.material.controller;

import com.vsj.material.model.VsjMaterial;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.model.request.VsjMaterialRequest;
import com.vsj.material.model.request.VsjMaterialRequestList;
import com.vsj.material.service.VsjMaterialService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Date :2019/8/13 13:57
 * @Describe:素材管理相关接口
 * @Classname VsjMaterialController
 * @Created by sxm
 */

@CrossOrigin
@RestController
@RequestMapping(value = "api/v1/material",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class VsjMaterialController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    VsjMaterialService vsjMaterialService;

    /**
     * @Description 获取素材列表
     * @Author  sxm
     * @Date 2019/8/13 14:00
     * @Return     java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "查询素材", notes = "查询素材", httpMethod = "POST")
    @PostMapping(value = "/getMaterialList")
    public Object getMaterialList(@RequestBody QueryStat queryStat){
        logger.info("查询素材列表入参",queryStat);
        queryStat.setPlatformCode(getPlatformCode());
        Object obj = vsjMaterialService.getMaterialList(queryStat);
       logger.info("测试素材列表出参",obj);
        return obj;
    }
    /**
     * @Description 修改素材信息,批量修改权重,批量审批
     * @Author  sxm
     * @Date 2019/8/13 16:45
     * @Return     java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "修改素材信息", notes = "修改素材信息", httpMethod = "POST")
    @PostMapping("/updateMaterial")
    public Object updateMaterial(@RequestBody VsjMaterialRequestList vsjMaterialRequestList){
        logger.info("修改素材信息入参",vsjMaterialRequestList);
        Object obj = vsjMaterialService.updateMaterials(vsjMaterialRequestList,getPlatformCode());
        logger.info("修改素材信息出差",obj);
        return obj;
    }

    /**
     * @Description 删除素材,批量删除素材
     * @Author  sxm
     * @Date 2019/8/14 11:45
     * @Return     java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "删除素材", notes = "删除素材", httpMethod = "POST")
    @PostMapping("/deleteMaterial")
    public Object deleteMaterial(@RequestBody QueryStat querySet){
        logger.info("删除素材出参",querySet);
        querySet.setPlatformCode(getPlatformCode());
        Object obj = vsjMaterialService.deleteMaterial(querySet);
        logger.info("删除素材出参",obj);
        return obj;
    }

    /**
     * @Description 插入素材
     * @Author  sxm
     * @Date 2019/8/14 14:30
     * @Return     java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "插入素材", notes = "插入素材", httpMethod = "POST")
    @PostMapping("insertMaterial")
    public Object insertMaterial(@RequestBody VsjMaterialRequest vsjMaterialRequest){
        logger.info("添加素材入参",vsjMaterialRequest);
        Object obj = vsjMaterialService.insertMaterial(vsjMaterialRequest,getPlatformCode(),getUserInfo());
        logger.info("添加素材出参",obj);
        return obj;
    }
    /**
     * @Description 素材一键保存计数
     * @Author  wd
     * @Date 2019/8/16 9：30
     * @Return     java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "一键保存计数", notes = "一键保存计数", httpMethod = "POST")
    @PostMapping(value = "/keepAllMaterialcount")
    public Object keepCountingt(@RequestParam ("mid") String mid){
        logger.info("一键保存计数入参",mid);
        Object object= vsjMaterialService.keepCountingt(mid);
        logger.info("一键保存计数出参",object);
        return object;
    }
    /**
     * @Description 添加素材收藏
     * @Author  wd
     * @Date 2019/8/16 1：30
     * @Return     java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "添加收藏", notes = "添加收藏", httpMethod = "POST")
    @PostMapping(value = "/addMaterialcountLike")
    public Object addLike(@RequestParam ("mid") String mid , @RequestParam("type") Long type ){
        Integer userid = getUserInfo();
       Integer code = getPlatformCode();
        logger.info("添加收藏入参",mid,type);
        Object object = vsjMaterialService.addLike(mid,userid,code,type);
        logger.info("添加收藏出参",object);
        return object;
    }    /**
     * @Description 查看我的收藏素材和全部素材
     * @Author  wd
     * @Date 2019/8/16 1：30
     * @Return     java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "查看我的收藏和全部素材", notes = "查看我的收藏和全部素材", httpMethod = "POST")
    @PostMapping(value = "/getMyMaterialList")
    public Object getMyMaterialList(QueryStat querySet){
        querySet.setUseId(getUserInfo());
        logger.info("查看我的收藏和全部素材入参",querySet);
        Object obj = vsjMaterialService.getMyMaterialList(querySet);
        logger.info("查看我的收藏和全部素材出参",obj);
        return obj;
    }
    /**
     * @Description 获取素材列表
     * @Author  wd
     * @Date 2019/8/26 13:30
     * @Return     java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "查询音频视频素材", notes = "查询音频视频素材", httpMethod = "POST")
    @PostMapping(value = "/getAudioVideoMaterialList")
    public Object getAudioVideoMaterialList( QueryStat queryStat){
        logger.info("查询素材列表入参",queryStat);
        queryStat.setPlatformCode(getPlatformCode());
        Object obj = vsjMaterialService.getAudioVideoMaterialList(queryStat);
        logger.info("测试素材列表出参",obj);
        return obj;
    }

}
