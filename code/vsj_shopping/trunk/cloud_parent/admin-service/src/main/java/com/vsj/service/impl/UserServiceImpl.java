package com.vsj.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.BaseController;
import com.vsj.common.constants.SysConfigConstants;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.SysUserModel;
import com.vsj.common.model.UserLoginModel;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.SysUserEdit;
import com.vsj.common.redis.client.RedisClient;
import com.vsj.common.redis.constant.RedisKeyConstant;
import com.vsj.common.utils.StringUtil;
import com.vsj.common.utils.UUIDGeneratorUtils;
import com.vsj.dao.SysPermissionDAO;
import com.vsj.dao.UserDAO;
import com.vsj.dao.VsjSysConfigDAO;
import com.vsj.dao.VsjSysDictionaryDAO;
import com.vsj.model.*;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.vsj.common.utils.TreeUtils.listToTree;

@Service
public class UserServiceImpl extends BaseController implements UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private VsjSysDictionaryDAO vsjSysDictionaryDAO;
    
    @Autowired
    private VsjSysConfigDAO vsjSysConfigDAO;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private SysPermissionDAO sysPermissionDAO;
    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> queryStatConvert;

    @Autowired
    private AbstractObjectConverter<SysUserEdit, VsjSysUser> sysUserConvert;
    @Override
    public BaseResponseParam<UserLoginModel> userLogin(UserLoginModel user) {
        VsjSysUser vsjSysUser = userDAO.userLogin(user.getUserName(),user.getPassWord());
        if(vsjSysUser == null){
            return BaseResponseParam .fail("用户名或密码错误....");
        }
        UserLoginModel userModel = new UserLoginModel();
        userModel.setUserId(vsjSysUser.getId());
        userModel.setNickName(vsjSysUser.getNickName());
        String token= UUIDGeneratorUtils.getUUIDLower();
        redisClient.set(RedisKeyConstant.USER_TOKEN+vsjSysUser.getId(),token,1L, TimeUnit.DAYS);
        userModel.setToken(token);
        userModel.setSysDictionary(vsjSysDictionaryDAO.selectSysDictionary());
        for (VsjSysRole role: vsjSysUser.getRoles()){
            Set<VsjSysPermission> permissions=sysPermissionDAO.getPermissionsByRoleId(role.getId());
            role.setPermission(permissions);
        }
        userModel.setRoles(vsjSysUser.getRoles());
        VsjSysConfig vsjSysConfig = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.SHOPPING_NAME, vsjSysUser.getPlatformCode());
        if(null !=  vsjSysConfig && StringUtil.isNoEmptyStr(vsjSysConfig.getValue())){
        	userModel.setShopName(vsjSysConfig.getValue());
        }
        return BaseResponseParam.success(userModel);
    }

    @Override
    public Object getSysUserList(QueryStat queryStat) {
        PageHelper.startPage(queryStat.getPageNum(),queryStat.getPageSize());
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        List<VsjSysUser> userList=userDAO.getSysUserList(baseQueryStat);
        if (userList.size()<0){
            return BaseResponseParam.fail();
        }
        List<SysUserModel>  list=new ArrayList<>();
        for (VsjSysUser vsjSysUser:userList) {
        SysUserModel userModel=new SysUserModel();
        userModel.setId(vsjSysUser.getId());
        userModel.setEmail(vsjSysUser.getEmail());
        userModel.setNickName(vsjSysUser.getNickName());
        userModel.setPhone(vsjSysUser.getPhone());
        userModel.setSex(vsjSysUser.getSex());
        userModel.setState(vsjSysUser.getState());
        userModel.setAccount(vsjSysUser.getAccount());
        userModel.setSysRoleModel(vsjSysUser.getRoles());
        for (VsjSysRole role: vsjSysUser.getRoles()){
            Set<VsjSysPermission> permissions=sysPermissionDAO.getPermissionsByRoleId(role.getId());
            role.setPermission(permissions);
        }
        list.add(userModel);
        }
        PageInfo<SysUserModel> pageInfo = new PageInfo<>(list);
        return BaseResponseParam.success(pageInfo);
    }

    @Override
    public BaseResponseParam addSysUser(SysUserEdit sysUserEdit) {
        VsjSysUser sysUser = sysUserConvert.convert(sysUserEdit, VsjSysUser.class);
        int i= userDAO.addSysUser(sysUser);
        if (i>0){
            return BaseResponseParam.success(sysUser.getId());
        }
        return BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam delSysUser(QueryStat queryStat) {
        if (queryStat.getIds()==null){
            return BaseResponseParam.fail("无效参数");
        }
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        int usrCount=userDAO.delSysUser(baseQueryStat);
        if (usrCount>0){
            //删除关联表中的用户角色数据
            userDAO.delSysUsrAndRole(baseQueryStat);
                    return BaseResponseParam.success();

        }
        return BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam getSysUserById(QueryStat queryStat) {
        if(queryStat.getId()==null){
            return BaseResponseParam.fail("无效参数");
        }
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        VsjSysUser sysUser=userDAO.getSysUserById(baseQueryStat);
        if (sysUser == null){
            return BaseResponseParam.fail();
        }
        if(sysUser.getRoles().size()>0) {
            for (VsjSysRole role : sysUser.getRoles()) {
                Set<VsjSysPermission> permissions = sysPermissionDAO.getPermissionsByRoleId(role.getId());
                role.setPermission(permissions);
            }
        }
        return BaseResponseParam.success(sysUser);
    }

    @Override
    public BaseResponseParam addUserROleJoin(SysUserEdit sysUserEdit) {
        VsjSysUser sysUser = sysUserConvert.convert(sysUserEdit, VsjSysUser.class);
        int i=userDAO.addUserROleJoin(sysUser.getId(),sysUser.getRoles(),sysUserEdit.getPlatformCode());
            if (i>0){
                return BaseResponseParam.success();
            }
        return BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam getPermissionList(Integer platformCode) {
        List<VsjSysPermission> list=userDAO.getPermissionList(platformCode);
        if (list.size()>0){
            String tree = listToTree(JSON.toJSONString(list),"id", "parentId", "children");
            list=JSON.parseArray(tree,VsjSysPermission.class);
            return BaseResponseParam.success(list);
        }
        return BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam updateSysUser(SysUserEdit sysUserEdit) {
        if (sysUserEdit.getId()==null){
            return BaseResponseParam.fail("无效参数");
        }
        VsjSysUser sysUser = sysUserConvert.convert(sysUserEdit, VsjSysUser.class);
        int i= userDAO.updateSysUser(sysUser);
        if (i>0){
            return BaseResponseParam.success();
        }
        return BaseResponseParam.fail();
    }
}
