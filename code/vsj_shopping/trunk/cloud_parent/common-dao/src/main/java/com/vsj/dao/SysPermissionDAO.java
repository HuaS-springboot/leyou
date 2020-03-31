package com.vsj.dao;

import com.vsj.mapper.SysPermissionMapper;
import com.vsj.model.VsjSysPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Classname SysPermission
 * @Description TODO
 * @Date 2019/8/20 17:59
 * @Created by zy
 */
@Component
public class SysPermissionDAO {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    public Set<VsjSysPermission> getPermissionsByRoleId(Integer id) {
        return sysPermissionMapper.getPermissionsByRoleId(id);
    }
}
