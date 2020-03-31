package com.vsj.material.service;

import com.vsj.material.model.VsjMaterialMember;
import com.vsj.material.model.request.*;

/**
 * @Classname VsjMaterialUserService
 * @Description 会员相关
 * @Date 2019/8/14 10:03
 * @Created by wangzx
 */
public interface VsjMaterialUserService {

    Object getUserList(QueryStat queryStat);

    Object updateUser(VsjMaterialUserRequest vsjMaterialUserRequest);

    Object updateMemberPackage(MaterialMemberPackageRequest materialMemberPackageRequest,Integer platformCode);

    Object getUserLevelList(QueryStat queryStat);

    Object updateUserLevel(MaterialUserLevelRequest materialUserLevelRequest);

    Object addUserLevel(MaterialUserLevelRequest materialUserLevelRequest);

    Object updateMemberPackage1(MemberPackageRequestList materialMemberPackageRequest, Integer platformCode);

    Object getMaterialPackage(QueryStat queryStat);

    Object deleteUserLevel(QueryStat querySet);
}
