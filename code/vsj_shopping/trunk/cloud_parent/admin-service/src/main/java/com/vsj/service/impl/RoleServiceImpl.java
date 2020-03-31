package com.vsj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.SysUserModel;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.RoleRequest;
import com.vsj.common.utils.StringUtil;
import com.vsj.dao.RoleDAO;
import com.vsj.dao.SysPermissionDAO;
import com.vsj.dao.UserDAO;
import com.vsj.model.VsjSysPermission;
import com.vsj.model.VsjSysRole;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @Classname RoleServiceImpl
 * @Description TODO
 * @Date 2019/8/20 17:17
 * @Created by zy
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private SysPermissionDAO sysPermissionDAO;

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> queryStatConvert;
    @Autowired
    private AbstractObjectConverter<RoleRequest, VsjSysRole> roleConvert;
    @Override
    public BaseResponseParam getRoleList(QueryStat queryStat) {
        PageHelper.startPage(queryStat.getPageNum(),queryStat.getPageSize());
        List<VsjSysRole> roleList=roleDAO.getRoleList(queryStat.getPlatformCode());
        if (roleList.size()>0){
            for (int i=0;i<roleList.size();i++){
                Set<VsjSysPermission> permissions=sysPermissionDAO.getPermissionsByRoleId(roleList.get(i).getId());
                roleList.get(i).setPermission(permissions);
            }
            PageInfo<VsjSysRole> pageInfo = new PageInfo<>(roleList);
            return BaseResponseParam.success(pageInfo);
        }
        return BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam getRoleById(QueryStat queryStat) {
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        VsjSysRole sysRole=roleDAO.getRoleById(baseQueryStat);
        if(sysRole == null){
            return BaseResponseParam.fail();
        }
        return BaseResponseParam.success(sysRole);
    }

    @Override
    public BaseResponseParam delRole(QueryStat queryStat) {
        if (StringUtil.isEmptyStr(queryStat.getIds())){
            return BaseResponseParam.fail("参数错误");
        }
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        int i = roleDAO.delRole(baseQueryStat);
        if (i>0){
            int j=userDAO.delRoleAndPermission(baseQueryStat.getId());
            if (j>0) {
                return BaseResponseParam.success();
            }
        }
        return BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam editRole(RoleRequest roleRequest) {
        VsjSysRole vsjSysRole = roleConvert.convert(roleRequest,VsjSysRole.class);
        if (vsjSysRole.getId()!=null){
            int i=roleDAO.editRole(vsjSysRole);
            if (i>0){
                return BaseResponseParam.success();
            }
        }else {
            int i=roleDAO.insertRole(vsjSysRole);
            if (i > 0){
                return BaseResponseParam.success(vsjSysRole.getId());
            }
        }

        return BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam addRolePermission(RoleRequest roleRequest) {
        VsjSysRole vsjSysRole = roleConvert.convert(roleRequest,VsjSysRole.class);
        int i=roleDAO.addUserROleJoin(vsjSysRole.getId(),vsjSysRole.getPlatformCode(),vsjSysRole.getPermission());
        if (i>0){
            return BaseResponseParam.success();
        }
        return BaseResponseParam.fail();
    }
}
