package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.UserLoginModel;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.SysUserEdit;

public interface UserService {
	BaseResponseParam userLogin(UserLoginModel user);

    Object getSysUserList(QueryStat queryStat);

    BaseResponseParam updateSysUser(SysUserEdit sysUserEdit);

    BaseResponseParam addSysUser(SysUserEdit sysUserEdit);

    BaseResponseParam delSysUser(QueryStat queryStat);

    BaseResponseParam getSysUserById(QueryStat queryStat);

    BaseResponseParam addUserROleJoin(SysUserEdit sysUserEdit);

    BaseResponseParam getPermissionList(Integer platformCode);
}
