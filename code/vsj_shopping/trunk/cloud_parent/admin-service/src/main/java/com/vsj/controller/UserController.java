package com.vsj.controller;


import com.alibaba.fastjson.JSON;
import com.vsj.common.BaseController;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.UserLoginModel;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.SysUserEdit;
import com.vsj.common.utils.StringUtil;
import com.vsj.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "api/v1/user/",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin
public class UserController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());     // extends BaseController

    @Autowired
    private UserService userService;

    @SuppressWarnings("rawtypes")
    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
	@PostMapping("checkLogin")
    @ResponseBody
    public BaseResponseParam<UserLoginModel> checkLogin(@RequestBody UserLoginModel user){
        logger.info("用户登录checkLogin入参={}",JSON.toJSONString(user));
        if(null == user || StringUtil.isEmptyStr(user.getUserName()) || StringUtil.isEmptyStr(user.getPassWord()) ){
        	return BaseResponseParam.fail("用户名或密码不能为空...");
        }
        BaseResponseParam result = userService.userLogin(user);
        logger.info("用户登录checkLogin出参={}",JSON.toJSONString(result));
        return result;

    }
    /**
    * @Description 查询系统用户列表
    * @Author zy
    * @Date   2019/8/12 9:20
    * @Param  fuzzyList 账号/姓名/手机
    * @Return
    * @Exception
    *
    */
    @ApiOperation(value = "查询系统用户列表", notes = "查询系统用户列表", httpMethod = "POST")
    @RequestMapping("getSysUserList")
    public Object getSysUserList(@RequestBody QueryStat queryStat){
        if(null == getPlatformCode()){
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("查询系统用户列表入参:"+queryStat);
        Object obj=userService.getSysUserList(queryStat);
        return obj;
    }

    /**
     * @Description 编辑用户信息
     * @Author zy
     * @Date   2019/8/20 9:21
     * @Param  [queryStat]
     * id ：pk
     * @Return      com.vsj.common.model.BaseResponseParam
     * @Exception
     *
     */
    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @RequestMapping("editSysUser")
    public BaseResponseParam editSysUser(@RequestBody SysUserEdit sysUserEdit){
        if(null == getPlatformCode()){
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        sysUserEdit.setPlatformCode(getPlatformCode());
        logger.info("修改用户信息入参:"+sysUserEdit);
        long startTime = System.currentTimeMillis();
        BaseResponseParam responseParam;
        if (sysUserEdit.getId()==null){
            responseParam=userService.addSysUser(sysUserEdit);
        }else{
            responseParam=userService.updateSysUser(sysUserEdit);
        }
        logger.info("修改用户信息请求完成,返回参数={},耗时={}", responseParam, (System.currentTimeMillis() - startTime));
        return responseParam;
    }
    /**
     * @Description 删除用户信息接口
     * @Author zy
     * @Date   2019/8/20 15:28
     * @Param  [queryStat]
     * ids :主键字符串
     * @Return      com.vsj.common.model.BaseResponseParam
     * @Exception
     *
     */
    @ApiOperation(value = "删除用户信息", notes = "删除用户信息", httpMethod = "POST")
    @RequestMapping("delSysUser")
    public BaseResponseParam delSysUser(@RequestBody QueryStat queryStat){

        if(null == getPlatformCode()){
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("删除用户信息入参:"+queryStat);
        long startTime = System.currentTimeMillis();
        BaseResponseParam responseParam=userService.delSysUser(queryStat);
        logger.info("修改用户信息请求完成,返回参数={},耗时={}", responseParam, (System.currentTimeMillis() - startTime));
        return responseParam;
    }
    /**
     * @Description 查询用户详情信息接口
     * @Author zy
     * @Date   2019/8/20 15:34
     * @Param  [queryStat]
     * id
     * @Return      com.vsj.common.model.BaseResponseParam
     * @Exception
     *
     */
    @ApiOperation(value = "查询系统用户信息", notes = "查询系统用户信息", httpMethod = "POST")
    @RequestMapping("getSysUserById")
    public BaseResponseParam getSysUserById(@RequestBody QueryStat queryStat){

        if(null == getPlatformCode()){
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        logger.info("查询系统用户信息入参:"+queryStat);
        queryStat.setPlatformCode(getPlatformCode());
        long startTime = System.currentTimeMillis();
        BaseResponseParam responseParam=userService.getSysUserById(queryStat);
        logger.info("查询用户信息请求完成,返回参数={},耗时={}", responseParam, (System.currentTimeMillis() - startTime));
        return responseParam;
    }

    @ApiOperation(value = "添加用户角色关联信息", notes = "添加用户角色关联信息", httpMethod = "POST")
    @RequestMapping("addUserRoleJoin")
    public BaseResponseParam addUserRoleJoin(@RequestBody SysUserEdit sysUserEdit){
        if(null == getPlatformCode()){
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        logger.info("添加用户角色关联信息入参:"+sysUserEdit);
        sysUserEdit.setPlatformCode(getPlatformCode());
        long startTime = System.currentTimeMillis();
        BaseResponseParam responseParam=userService.addUserROleJoin(sysUserEdit);
        logger.info("添加用户角色关联信息请求完成,返回参数={},耗时={}", responseParam, (System.currentTimeMillis() - startTime));
        return responseParam;
    }

    @ApiOperation(value = "查询权限菜单列表", notes = "查询权限菜单列表", httpMethod = "POST")
    @RequestMapping("getPermissionList")
    public BaseResponseParam getPermissionList(){
        if(null == getPlatformCode()){
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        long startTime = System.currentTimeMillis();
        BaseResponseParam responseParam=userService.getPermissionList(getPlatformCode());
        logger.info("查询权限菜单列表请求完成,返回参数={},耗时={}", responseParam, (System.currentTimeMillis() - startTime));
        return responseParam;
    }

}
