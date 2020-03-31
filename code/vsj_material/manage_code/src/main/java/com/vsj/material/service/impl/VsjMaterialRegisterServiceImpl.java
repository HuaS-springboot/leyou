package com.vsj.material.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.material.dao.VsjMaterialRegisterDAO;
import com.vsj.material.model.VsjMaterialRegister;
import com.vsj.material.model.convert.AbstractObjectConverter;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.model.request.VsjMaterialRegisterRequest;
import com.vsj.material.service.VsjMaterialRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname VsjMaterialRegisterServiceImpl
 * @Description 注册相关实现类
 * @Date 2019/8/14 16:35
 * @Created by wangzx
 */
@Service
public class VsjMaterialRegisterServiceImpl implements VsjMaterialRegisterService {

    @Autowired
    private VsjMaterialRegisterDAO vsjMaterialRegisterDAO;

    @Autowired
    private AbstractObjectConverter<VsjMaterialRegisterRequest,VsjMaterialRegister> converter;

    @Override
    public Object updateRegisterById(VsjMaterialRegister vsjMaterialRegister) {
        return vsjMaterialRegisterDAO.updateRegisterById(vsjMaterialRegister);
    }

    @Override
    public Object insertRegister(VsjMaterialRegisterRequest vsjMaterialRegisterRequest,Integer platformCode) {
        VsjMaterialRegister register = converter.convert(vsjMaterialRegisterRequest,VsjMaterialRegister.class);
        register.setPlatformCode( platformCode);
        int count = vsjMaterialRegisterDAO.insertRegister(register);
        if(count>0){
            return BaseResponseParam.success(true);
        }
        return BaseResponseParam.fail(false);
    }

    @Override
    public Object getRegister(QueryStat queryStat) {
        PageHelper.startPage(queryStat.getPageNum(),queryStat.getPageSize());
        List<VsjMaterialRegister> registers = vsjMaterialRegisterDAO.getRegister(queryStat);
        PageInfo<VsjMaterialRegister> pageInfo = new PageInfo<>(registers);
        if(pageInfo != null){
            return BaseResponseParam.success(pageInfo);
        }
        return BaseResponseParam.fail("未查询到数据");
    }


}
