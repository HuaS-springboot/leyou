package com.vsj.material.dao;

import com.vsj.material.mapper.MaterialPackageMapper;
import com.vsj.material.model.MaterialMemberPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname MaterialPackageDAO
 * @Description 套餐dao
 * @Date 2019/8/20 17:29
 * @Created by wangzx
 */
@Component
public class MaterialPackageDAO  {

    @Autowired
    private MaterialPackageMapper materialPackageMapper;


    public MaterialMemberPackage getMaterialPackageById(Integer packageId, Integer platformCode) {
        return materialPackageMapper.getMaterialPackageById(packageId,platformCode);
    }
}
