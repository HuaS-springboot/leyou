package com.vsj.controller;

import com.vsj.common.BaseController;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.RoleRequest;
import com.vsj.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname RoleController
 * @Description TODO
 * @Date 2019/8/20 17:15
 * @Created by zy
 */
@RestController
@RequestMapping(value = "api/v1/role/",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin
public class RoleController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RoleService roleService;
    /**
     * @Description 查询角色列表
     * @Author zy
     * @Date   2019/8/20 17:45
     * @Param  []
     * @Return      com.vsj.common.model.BaseResponseParam
     * @Exception
     *
     */
    @ApiOperation(value = "查询角色列表请求", notes = "查询角色列表请求", httpMethod = "POST")
    @RequestMapping("getRoleList")
    public BaseResponseParam getRoleList(@RequestBody QueryStat queryStat){
        if(null == getPlatformCode()){
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        queryStat.setPlatformCode(getPlatformCode());
        long startTime = System.currentTimeMillis();
        BaseResponseParam responseParam=roleService.getRoleList(queryStat);
        logger.info("查询角色列表请求完成,返回参数={},耗时={}", responseParam, (System.currentTimeMillis() - startTime));
        return responseParam;
    }

    /**
     * @Description 查询角色信息
     * @Author zy
     * @Date   2019/8/20 18:24
     * @Param  [queryStat]
     * id:角色ID
     * @Return      com.vsj.common.model.BaseResponseParam
     * @Exception
     *
     */
    @ApiOperation(value = "查询系统角色信息", notes = "查询系统角色信息", httpMethod = "POST")
    @RequestMapping("getRoleById")
    public BaseResponseParam getRoleById(@RequestBody QueryStat queryStat) {

        if (null == getPlatformCode()) {
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        logger.info("查询系统角色信息入参:" + queryStat);
        queryStat.setPlatformCode(getPlatformCode());
        long startTime = System.currentTimeMillis();
        BaseResponseParam responseParam = roleService.getRoleById(queryStat);
        logger.info("查询角色信息请求完成,返回参数={},耗时={}", responseParam, (System.currentTimeMillis() - startTime));
        return responseParam;
    }

    /**
    * @Description 删除角色信息
    * @Author zy
    * @Date   2019/8/20 18:44
    * @Param
     * ids : id字符串
    * @Return
    * @Exception
    *
    */
    @ApiOperation(value = "删除角色信息", notes = "删除角色信息", httpMethod = "POST")
    @RequestMapping("delRole")
    public BaseResponseParam delRole(@RequestBody QueryStat queryStat) {
        if (null == getPlatformCode()) {
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        logger.info("删除角色信息入参:" + queryStat);
        queryStat.setPlatformCode(getPlatformCode());
        long startTime = System.currentTimeMillis();
        BaseResponseParam responseParam = roleService.delRole(queryStat);
        logger.info("删除角色请求完成,返回参数={},耗时={}", responseParam, (System.currentTimeMillis() - startTime));
        return responseParam;
    }

    @ApiOperation(value = "编辑角色信息", notes = "编辑角色信息", httpMethod = "POST")
    @RequestMapping("editRole")
    public BaseResponseParam editRole(@RequestBody RoleRequest roleRequest) {
        if (null == getPlatformCode()) {
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        logger.info("编辑角色信息入参:" + roleRequest);
        roleRequest.setPlatformCode(getPlatformCode());
        long startTime = System.currentTimeMillis();
        BaseResponseParam responseParam = roleService.editRole(roleRequest);
        logger.info("编辑角色请求完成,返回参数={},耗时={}", responseParam, (System.currentTimeMillis() - startTime));
        return responseParam;
    }
    @ApiOperation(value = "添加角色权限关联信息", notes = "添加角色权限关联信息", httpMethod = "POST")
    @RequestMapping("addRolePermission")
    public BaseResponseParam addRolePermission(@RequestBody RoleRequest roleRequest){
        if (null == getPlatformCode()) {
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        logger.info("添加角色权限信息入参:" + roleRequest);
        roleRequest.setPlatformCode(getPlatformCode());
        long startTime = System.currentTimeMillis();
        BaseResponseParam responseParam = roleService.addRolePermission(roleRequest);
        logger.info("添加角色权限信息请求完成,返回参数={},耗时={}", responseParam, (System.currentTimeMillis() - startTime));
        return responseParam;
    }
}
