package com.vsj.material.dao;

import com.vsj.material.mapper.VsjMaterialUserMapper;
import com.vsj.material.model.MaterialMemberPackage;
import com.vsj.material.model.MaterialUserLevel;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.model.response.MaterialMemberPackageResponse;
import com.vsj.material.model.response.MaterialUserLevelResponse;
import com.vsj.material.model.response.VsjMaterialUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname VsjMaterialUserDAO
 * @Description 会员DAO
 * @Date 2019/8/14 10:04
 * @Created by wangzx
 */
@Component
public class VsjMaterialUserDAO {

    @Autowired
    private VsjMaterialUserMapper vsjMaterialUserMapper;

    public List<VsjMaterialUserResponse> getUserList(QueryStat queryStat) {
        return vsjMaterialUserMapper.getUserList(queryStat);
    }

    public int updateMemberPackage(MaterialMemberPackage materialMemberPackage) {
        return vsjMaterialUserMapper.updateMemberPackage(materialMemberPackage);
    }

    public List<MaterialUserLevelResponse> getUserLevelList(QueryStat queryStat) {
        return vsjMaterialUserMapper.getUserLevelList(queryStat);
    }

    public int updateUserLevel(MaterialUserLevel materialUserLevel) {
        return vsjMaterialUserMapper.updateUserLevel(materialUserLevel);
    }

    public int addUserLevel(MaterialUserLevel materialUserLevel) {
        return vsjMaterialUserMapper.addUserLevel(materialUserLevel);
    }

    public List<MaterialMemberPackageResponse> getMaterialPackage(QueryStat queryStat) {
        return vsjMaterialUserMapper.getMaterialPackage(queryStat);
    }

    public int deleteUserLevel(QueryStat querySet) {
        return vsjMaterialUserMapper.deleteUserLevel(querySet);
    }
}
