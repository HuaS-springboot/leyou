package com.vsj.material.dao;

import com.vsj.material.mapper.VsjMaterialRegisterMapper;
import com.vsj.material.model.VsjMaterialRegister;
import com.vsj.material.model.request.QueryStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname VsjMaterialRegisterDAO
 * @Description 注册相关dao
 * @Date 2019/8/14 16:35
 * @Created by wangzx
 */
@Component
public class VsjMaterialRegisterDAO {

    @Autowired
    private VsjMaterialRegisterMapper vsjMaterialRegisterMapper;


    public int updateRegisterById(VsjMaterialRegister vsjMaterialRegister) {
        return vsjMaterialRegisterMapper.updateRegisterById(vsjMaterialRegister);
    }

    public int insertRegister(VsjMaterialRegister vsjMaterialRegister){
        return vsjMaterialRegisterMapper.insertRegister(vsjMaterialRegister);
    }

    public List<VsjMaterialRegister> getRegister(QueryStat queryStat){
        return vsjMaterialRegisterMapper.selectRegister(queryStat);
    }
}
