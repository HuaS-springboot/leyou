package com.vsj.material.service;

import com.vsj.material.model.VsjMaterialRegister;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.model.request.VsjMaterialRegisterRequest;

/**
 * @Classname VsjMaterialRegisterService
 * @Description 注册相关接口
 * @Date 2019/8/14 16:34
 * @Created by wangzx
 */
public interface VsjMaterialRegisterService {

    Object updateRegisterById(VsjMaterialRegister vsjMaterialRegister);

    Object insertRegister(VsjMaterialRegisterRequest vsjMaterialRegisterRequest,Integer platformCode);

    Object getRegister(QueryStat queryStat);
}
