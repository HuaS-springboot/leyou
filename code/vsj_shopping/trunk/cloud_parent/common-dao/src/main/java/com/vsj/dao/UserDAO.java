package com.vsj.dao;

import com.vsj.mapper.UserMapper;
import com.vsj.model.*;
import com.vsj.model.request.BaseQueryStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @Classname UserDAO
 * @Description TODO
 * @Date 2019/7/31 17:21
 * @Created by zy
 */
@Component
public class UserDAO {

    @Autowired
    private UserMapper userMapper;

    public VsjRegister getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    public VsjSysUser userLogin(String userName,String passWord){
        return userMapper.userLogin(userName,passWord);
    }

    public VsjSysUser findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    public VsjSysUser findByMemberName(String userName) {
        return userMapper.findByUserName(userName);
    }


    public List<VsjSysUser> getSysUserList(BaseQueryStat baseQueryStat) {
        return userMapper.getSysUserList(baseQueryStat);
    }

    public int addSysUser(VsjSysUser sysUser) {
        return userMapper.addSysUser(sysUser);
    }

    public int updateSysUser(VsjSysUser sysUser) {
        return userMapper.updateSysUser(sysUser);
    }

    public int delSysUser(BaseQueryStat baseQueryStat) {
        return userMapper.delSysUser(baseQueryStat);
    }

    public VsjSysUser getSysUserById(BaseQueryStat baseQueryStat) {
        return userMapper.getSysUserById(baseQueryStat);
    }

    public void delSysUsrAndRole(BaseQueryStat baseQueryStat) {
         userMapper.delSysUsrAndRole(baseQueryStat);
    }


    public int delRoleAndPermission(Integer id) {
        return userMapper.delRoleAndPermission(id);
    }

    public int addUserROleJoin(Integer id,Set<VsjSysRole> sets,Integer platformCode) {
        return userMapper.addUserROleJoin(id,sets,platformCode);
    }

    public List<VsjSysPermission> getPermissionList(Integer platformCode) {
        return userMapper.getPermissionList(platformCode);
    }
}
