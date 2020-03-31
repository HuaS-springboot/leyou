package com.vsj.common.model.convert;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.request.RoleRequest;
import com.vsj.model.VsjSysRole;
import org.springframework.stereotype.Component;

/**
 * @Classname RoleConvert
 * @Description TODO
 * @Date 2019/8/22 15:19
 * @Created by zy
 */
@Component
public class RoleConvert extends AbstractObjectConverter<RoleRequest, VsjSysRole> {
    @Override
    protected void convertImpl(RoleRequest source, VsjSysRole target) {
        target.setCreateId(source.getCreateId());
        target.setCreateTime(source.getCreateTime());
        target.setId(source.getId());
        target.setPlatformCode(source.getPlatformCode());
        target.setRoleCode(source.getRoleCode());
        target.setRoleName(source.getRoleName());
        target.setState(source.getState());
        target.setPermission(source.getPermissions());
    }

    @Override
    protected void reConvertImpl(VsjSysRole source, RoleRequest target) {

    }
}
