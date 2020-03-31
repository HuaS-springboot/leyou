package com.vsj.mapper;

import com.vsj.model.VsjSysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * @Classname SysPermissionMapper
 * @Description TODO
 * @Date 2019/8/20 18:03
 * @Created by zy
 */
@Mapper
public interface SysPermissionMapper {

    @Select("SELECT r.* FROM vsj_sys_permission r LEFT JOIN vsj_sys_role_permission p ON p.pid = r.id WHERE p.rid = #{id}")
    Set<VsjSysPermission> getPermissionsByRoleId(Integer id);
}
