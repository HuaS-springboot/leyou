package com.vsj.material.controller;

import com.vsj.material.model.request.*;
import com.vsj.material.service.VsjMaterialUserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname UserController
 * @Description 会员相关
 * @Date 2019/8/14 9:54
 * @Created by wangzx
 */
@RestController
@RequestMapping(value = "api/v1/admin/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin
public class UserController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private VsjMaterialUserService vsjMaterialUserService;


    /**
     * @Description 查询会员列表
     * @Author  wangzx
     * @Date   2019/8/14 9:56
     * @Param  [queryStat]
     *
     * @Return  java.lang.Object
     * @Exception
     */
    @ApiOperation(value = "查询会员列表", notes = "查询会员列表", httpMethod = "POST")
    @PostMapping("/getUserList")
    public Object getUserList(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("查询会员列表入参={}",queryStat);
        Object obj = vsjMaterialUserService.getUserList(queryStat);
        logger.info("查询会员列表出参={}",obj);
        return obj;
    }
    /**
     * @Description 查询会员套餐列表
     * @Author  wd
     * @Date   2019/8/26 9:56
     * @Param  [queryStat]
     *
     * @Return  java.lang.Object
     * @Exception
     */
    @PostMapping("/MaterialPackage")
    public Object getMaterialPackage( QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("查询会员套餐列表入参={}",queryStat);
        Object obj = vsjMaterialUserService.getMaterialPackage(queryStat);
        logger.info("查询会员套餐列表出参={}",obj);
        return obj;
    }

    /**
     * @Description  修改会员信息
     * @Author  wangzx
     * @Date   2019/8/14 16:11
     * @Param  [vsjMaterialUserRequest]
     * id:会员id 注册表的id
     * headUrl:头像
     * nickName：昵称
     * createTime：创建时间
     * levelId：等级id
     * expiredTime:过期时间
     * telPhone:手机号
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "修改会员列表", notes = "修改会员列表", httpMethod = "POST")
    @PostMapping("/updateUser")
    public Object updateUser(@RequestBody VsjMaterialUserRequest vsjMaterialUserRequest){
        vsjMaterialUserRequest.setPlatformCode(getPlatformCode());
        logger.info("修改会员信息入参={}",vsjMaterialUserRequest);
        Object obj = vsjMaterialUserService.updateUser(vsjMaterialUserRequest);
        logger.info("修改会员信息出参={}",obj);
        return obj;
    }

    /**
     * @Description  修改会员套餐配置
     * @Author  wd
     * @Date   2019/8/23 13:55
     * @Param  MemberPackageRequestList
     * status会员状态 状态 是否支持购买 0=不支持购买;1=支持购买
     * memberPackageList 会员信息集合
     * @Return      java.lang.Object
     * @Exception
     */
    @ApiOperation(value = "修改会员套餐配置", notes = "修改会员套餐配置", httpMethod = "POST")
    @PostMapping("/updateMemberPackage")
    public Object updateMemberPackage(@RequestBody MemberPackageRequestList materialMemberPackageRequest){
        logger.info("修改会员套餐配置入参={}",materialMemberPackageRequest);
        Integer platformCode = getPlatformCode();
        Object object=  vsjMaterialUserService.updateMemberPackage1(materialMemberPackageRequest, platformCode);
        logger.info("修改会员套餐配置出参={}",object);
        return object;

    }
    /**
     * @Description 查询会员等级列表
     * @Author  wd
     * @Date   2019/8/22 11：30
     * @Param  [queryStat]
     *
     * @Return  java.lang.Object
     * @Exception
     */
    @ApiOperation(value = "查询会员等级列表", notes = "查询会员等级列表", httpMethod = "POST")
    @PostMapping("/getUserLevelList")
    public Object getUserLevelList(QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("查询会员等级列表入参={}",queryStat);
        Object obj = vsjMaterialUserService.getUserLevelList(queryStat);
        logger.info("查询会员等级列表出参={}",obj);
        return obj;
    }
    /**
     * @Description 修改会员等级信息
     * @Author  wd
     * @Date   2019/8/22 14：00
     * @Param  [MaterialUserLevelRequest]
     *
     * @Return  java.lang.Object
     * @Exception
     */
    @ApiOperation(value = "修改会员等级", notes = "修改会员等级", httpMethod = "POST")
    @PostMapping("/updateUserLevel")
    public Object updateUserLevel(@RequestBody MaterialUserLevelRequest materialUserLevelRequest){
        materialUserLevelRequest.setPlatformCode(getPlatformCode());
        logger.info("修改会员等级信息入参={}",materialUserLevelRequest);
        Object obj = vsjMaterialUserService.updateUserLevel(materialUserLevelRequest);
        logger.info("修改会员等级信息出参={}",obj);
        return obj;
    }
    /**
     * @Description 新增会员等级信息
     * @Author  wd
     * @Date   2019/8/22 15：00
     * @Param  [MaterialUserLevelRequest]
     *
     * @Return  java.lang.Object
     * @Exception
     */
    @ApiOperation(value = "新增会员等级信息", notes = "新增会员信息", httpMethod = "POST")
    @PostMapping("/addUserLevel")
    public Object addUserLevel(@RequestBody MaterialUserLevelRequest materialUserLevelRequest){
        materialUserLevelRequest.setPlatformCode(getPlatformCode());
        logger.info("新增会员信息入参={}",materialUserLevelRequest);
        Object obj = vsjMaterialUserService.addUserLevel(materialUserLevelRequest);
        logger.info("新增会员信息出参={}",obj);
        return obj;
    }
    /**
     * @Description 批量 删除会员等级信息
     * @Author  wd
     * @Date   2019/8/26 17：00
     * @Param  [querySet] ids
     *
     * @Return  java.lang.Object
     * @Exception
     */
    @ApiOperation(value = "删除会员等级信息", notes = "删除会员信息", httpMethod = "POST")
    @PostMapping("/deleteUserLevel")
    public Object deleteUserLevel(@RequestBody QueryStat querySet){
        logger.info("删除素材出参",querySet);
        querySet.setPlatformCode(getPlatformCode());
        Object obj = vsjMaterialUserService.deleteUserLevel(querySet);
        logger.info("删除素材出参",obj);
        return obj;
    }

}
