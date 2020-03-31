package com.vsj.material.service.impl;

import com.vsj.material.dao.MaterialPackageDAO;
import com.vsj.material.model.MaterialMemberPackage;
import com.vsj.material.service.MaterialPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname MaterialPackageServiceImpl
 * @Description 套餐相关
 * @Date 2019/8/20 17:08
 * @Created by wangzx
 */
@Service
public class MaterialPackageServiceImpl implements MaterialPackageService {

    @Autowired
    private MaterialPackageDAO materialPackageDAO;


    @Override
    public MaterialMemberPackage getMaterialPackageById(Integer packageId, Integer platformCode) {
        return materialPackageDAO.getMaterialPackageById(packageId,platformCode);
    }
}
