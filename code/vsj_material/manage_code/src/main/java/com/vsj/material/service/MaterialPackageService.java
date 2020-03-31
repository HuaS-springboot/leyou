package com.vsj.material.service;

import com.vsj.material.model.MaterialMemberPackage;

/**
 * @Classname MaterialPackageService
 * @Description 套餐相关
 * @Date 2019/8/20 17:07
 * @Created by wangzx
 */
public interface MaterialPackageService {

    MaterialMemberPackage getMaterialPackageById(Integer packageId, Integer platformCode);
}
