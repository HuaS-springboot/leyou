package com.vsj.dao;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.mapper.RoleMapper;
import com.vsj.model.VsjSysPermission;
import com.vsj.model.VsjSysRole;
import com.vsj.model.request.BaseQueryStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @Classname RoleDAO
 * @Description TODO
 * @Date 2019/8/20 17:17
 * @Created by zy
 */
@Component
public class RoleDAO {

    @Autowired
    private RoleMapper roleMapper;
    public List<VsjSysRole> getRoleList(Integer platformCode) {
        return roleMapper.getRoleList(platformCode);
    }

    public VsjSysRole getRoleById(BaseQueryStat baseQueryStat) {
        return roleMapper.getRoleById(baseQueryStat);
    }

    public int delRole(BaseQueryStat baseQueryStat) {
        return roleMapper.delRole(baseQueryStat);
    }

    public int editRole(VsjSysRole vsjSysRole) {
        return roleMapper.editRole(vsjSysRole);
    }

    public int insertRole(VsjSysRole vsjSysRole) {
        return roleMapper.insertRole(vsjSysRole);
    }

    public int addUserROleJoin(Integer id,Integer platformCode, Set<VsjSysPermission> permissions) {
        return roleMapper.addUserROleJoin(id,platformCode,permissions);
    }
}
