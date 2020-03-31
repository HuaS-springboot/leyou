package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.RoleRequest;

/**
 * @Classname RoleService
 * @Description TODO
 * @Date 2019/8/20 17:16
 * @Created by zy
 */
public interface RoleService {
    BaseResponseParam getRoleList(QueryStat queryStat);

    BaseResponseParam getRoleById(QueryStat queryStat);

    BaseResponseParam delRole(QueryStat queryStat);

    BaseResponseParam editRole(RoleRequest roleRequest);

    BaseResponseParam addRolePermission(RoleRequest roleRequest);
}
