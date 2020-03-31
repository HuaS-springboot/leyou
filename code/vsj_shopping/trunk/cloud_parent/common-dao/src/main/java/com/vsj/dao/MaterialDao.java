package com.vsj.dao;


import com.vsj.mapper.MaterialMapper;
import com.vsj.model.VsjMaterial;

import com.vsj.model.request.BaseQueryStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * @Author: HuaS
 * @Date :2019/8/8 9:59
 * @Describe:
 */
@Component
public class MaterialDao {
    @Autowired
    private MaterialMapper materialMapper;

    public List<VsjMaterial> getMaterialList (){
        return materialMapper.getMaterialList();
    }

    public int insertMaterial(VsjMaterial vsjMaterial){
        return materialMapper.insertMaterial(vsjMaterial);
    }

    public int updateMaterial(VsjMaterial vsjMaterial){
        return materialMapper.updateMaterial(vsjMaterial);
    }

    public int deleteMaterial(VsjMaterial vsjMaterial){
        return materialMapper.deleteMaterial(vsjMaterial);
    }

}
