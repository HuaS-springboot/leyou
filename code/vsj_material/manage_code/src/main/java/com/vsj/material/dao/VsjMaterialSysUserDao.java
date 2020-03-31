package com.vsj.material.dao;

import com.vsj.material.mapper.VsjMaterialSysUserMapper;
import com.vsj.material.model.VsjMaterialSysUser;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.model.response.UserLoginModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VsjMaterialSysUserDao {

    @Autowired
    private VsjMaterialSysUserMapper vsjMaterialSysUserMapper;

    public UserLoginModel loginSysUser(QueryStat querySet){
        return vsjMaterialSysUserMapper.selectSysUser(querySet);
    }

}
