package com.vsj.material.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.utils.BeanHelper;
import com.vsj.material.dao.VsjMaterialUserDAO;
import com.vsj.material.model.*;
import com.vsj.material.model.convert.MaterialMemberPackageConvert;
import com.vsj.material.model.request.*;
import com.vsj.material.model.response.MaterialMemberPackageResponse;
import com.vsj.material.model.response.MaterialUserLevelResponse;
import com.vsj.material.model.response.VsjMaterialUserResponse;
import com.vsj.material.service.VsjMaterialMemberService;
import com.vsj.material.service.VsjMaterialRegisterService;
import com.vsj.material.service.VsjMaterialUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Classname VsjMaterialUserServiceImpl
 * @Description 会员相关实现类
 * @Date 2019/8/14 10:03
 * @Created by wangzx
 */
@Service
public class VsjMaterialUserServiceImpl implements VsjMaterialUserService {

    @Autowired
    private VsjMaterialUserDAO vsjMaterialUserDAO;
    @Autowired
    private VsjMaterialRegisterService vsjMaterialRegisterService;
    @Autowired
    private VsjMaterialMemberService vsjMaterialMemberService;
    @Autowired
    private MaterialMemberPackageConvert converter;


    @Override
    public Object getUserList(QueryStat queryStat) {
        if (queryStat.getPlatformCode() == null) {
            return BaseResponse.fail("参数错误");
        }
        PageHelper.startPage(queryStat.getPageNum(), queryStat.getPageSize());
        List<VsjMaterialUserResponse> list = vsjMaterialUserDAO.getUserList(queryStat);
        PageInfo<VsjMaterialUserResponse> pageInfo = new PageInfo<>(list);
        return BaseResponseParam.success(pageInfo);
    }

    @Override
    public Object updateUser(VsjMaterialUserRequest vsjMaterialUserRequest) {
        if (vsjMaterialUserRequest == null || vsjMaterialUserRequest.getId() == null ||
                vsjMaterialUserRequest.getPlatformCode() == null) {
            return BaseResponse.fail("参数错误");
        }
        // 修改register表的信息
        updateRegister(vsjMaterialUserRequest);
        // 修改member表的信息
        updateMember(vsjMaterialUserRequest);
        return BaseResponse.success();
    }

    @Override
    public Object updateMemberPackage(MaterialMemberPackageRequest materialMemberPackageRequest, Integer platformCode) {
        if (platformCode==null || materialMemberPackageRequest .getId()==null){
            return BaseResponseParam.fail("参数错误");
        }
        if (materialMemberPackageRequest.getDay()==null){
            return BaseResponseParam.fail("天数不能为空");
        }
        MaterialMemberPackage materialMemberPackage = converter.convert(materialMemberPackageRequest, MaterialMemberPackage.class);
        materialMemberPackage.setPlatformCode(platformCode);
       int i = vsjMaterialUserDAO.updateMemberPackage(materialMemberPackage);
       return BaseResponseParam.success();
    }

    @Override
    public Object getUserLevelList(QueryStat queryStat) {
        if (queryStat.getPlatformCode()==null){
            return BaseResponseParam.fail("参数错误");
        }
        PageHelper.startPage(queryStat.getPageNum(), queryStat.getPageSize());
        List<MaterialUserLevelResponse> list= vsjMaterialUserDAO.getUserLevelList(queryStat);
        PageInfo<MaterialUserLevelResponse> materialUserLevelResponsePageInfo = new PageInfo<>(list);
        return BaseResponseParam.success(materialUserLevelResponsePageInfo);
    }

    @Override
    public Object updateUserLevel(MaterialUserLevelRequest materialUserLevelRequest) {
        if (materialUserLevelRequest.getPlatformCode()==null){
            return BaseResponseParam.fail("参数错误");
        }
        MaterialUserLevel materialUserLevel = BeanHelper.copyProperties(materialUserLevelRequest, MaterialUserLevel.class);
        System.out.println(materialUserLevel);
        int i = vsjMaterialUserDAO.updateUserLevel(materialUserLevel);
        return BaseResponseParam.success();
    }

    @Override
    public Object addUserLevel(MaterialUserLevelRequest materialUserLevelRequest) {
        if (materialUserLevelRequest.getPlatformCode()==null){
            return BaseResponseParam.fail("参数错误");
        }
        MaterialUserLevel materialUserLevel = BeanHelper.copyProperties(materialUserLevelRequest, MaterialUserLevel.class);
        if(materialUserLevel.getIsDefault()==null){
            materialUserLevel.setIsDefault(0);
        }
        int i = vsjMaterialUserDAO.addUserLevel(materialUserLevel);
        return BaseResponseParam.success();
    }

    @Override
    public Object updateMemberPackage1(MemberPackageRequestList materialMemberPackageRequest, Integer platformCode) {
        if (platformCode==null || materialMemberPackageRequest.getStatus()==null){
            return BaseResponseParam.fail("参数错误");
        }
        List<MaterialMemberPackageRequest> memberPackageList = materialMemberPackageRequest.getMemberPackageList();
        for (MaterialMemberPackageRequest memberPackageRequest : memberPackageList){
            MaterialMemberPackage materialMemberPackage = converter.convert(memberPackageRequest, MaterialMemberPackage.class);
            materialMemberPackage.setPlatformCode(platformCode);
            materialMemberPackage.setStatus(materialMemberPackageRequest.getStatus());
            int i = vsjMaterialUserDAO.updateMemberPackage(materialMemberPackage);
            if (i!=1){
                return BaseResponseParam.fail("保存失败");
            }
        }
        return BaseResponseParam.success();
    }

    @Override
    public Object getMaterialPackage(QueryStat queryStat) {
        if (queryStat.getPlatformCode()==null){
            return BaseResponseParam.fail("参数错误");
        }
        PageHelper.startPage(queryStat.getPageNum(), queryStat.getPageSize());
        List<MaterialMemberPackageResponse> list= vsjMaterialUserDAO.getMaterialPackage(queryStat);
        PageInfo<MaterialMemberPackageResponse> materialUserLevelResponsePageInfo = new PageInfo<>(list);
        return BaseResponseParam.success(materialUserLevelResponsePageInfo);
    }

    @Override
    public Object deleteUserLevel(QueryStat querySet) {
        if (querySet.getIds().isEmpty() || querySet.getPlatformCode() == null) {
            return BaseResponse.fail("参数错误");
        }
        int i = vsjMaterialUserDAO.deleteUserLevel(querySet);
        return BaseResponse.success();

    }

    private void updateRegister(VsjMaterialUserRequest vsjMaterialUserRequest) {
        VsjMaterialRegister vsjMaterialRegister = new VsjMaterialRegister();
        vsjMaterialRegister.setId(vsjMaterialUserRequest.getId());
        vsjMaterialRegister.setNickName(vsjMaterialUserRequest.getNickName());
        vsjMaterialRegister.setHeadUrl(vsjMaterialUserRequest.getHeadUrl());
        vsjMaterialRegister.setCreateTime(vsjMaterialUserRequest.getCreateTime());
        vsjMaterialRegister.setPlatformCode(vsjMaterialUserRequest.getPlatformCode());
        vsjMaterialRegisterService.updateRegisterById(vsjMaterialRegister);
    }

    private void updateMember(VsjMaterialUserRequest vsjMaterialUserRequest) {
        VsjMaterialMember vsjMaterialMember = new VsjMaterialMember();
        vsjMaterialMember.setRegId(vsjMaterialUserRequest.getId());
        vsjMaterialMember.setLevelId(vsjMaterialUserRequest.getLevelId());
        vsjMaterialMember.setExpiredTime(vsjMaterialUserRequest.getExpiredTime());
        vsjMaterialMember.setPlatformCode(vsjMaterialUserRequest.getPlatformCode());
        vsjMaterialMemberService.updateMaterialMember(vsjMaterialMember);
    }
}
