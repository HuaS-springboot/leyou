package com.vsj.common.model.convert;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.request.SysUserEdit;
import com.vsj.model.VsjSysUser;
import org.springframework.stereotype.Component;

/**
 * @Classname VsjSysUserConvert
 * @Description TODO
 * @Date 2019/8/20 14:43
 * @Created by zy
 */
@Component
public class VsjSysUserConvert extends AbstractObjectConverter<SysUserEdit, VsjSysUser> {
    @Override
    protected void convertImpl(SysUserEdit source, VsjSysUser target) {
        target.setId(source.getId());
        target.setCreateId(source.getCreateId());
        target.setCreateTime(source.getCreateTime());
        target.setEmail(source.getEmail());
        target.setImgUrl(source.getImgUrl());
        target.setLastModifyTime(source.getLastModifyTime());
        target.setModifier(source.getModifier());
        target.setNickName(source.getNickName());
        target.setPhone(source.getPhone());
        target.setPlatformCode(source.getPlatformCode());
        target.setPswd(source.getPswd());
        target.setRemark(source.getRemark());
        target.setSex(source.getSex());
        target.setState(source.getState());
        target.setAccount(source.getAccount());
        target.setRoles(source.getRoles());
    }

    @Override
    protected void reConvertImpl(VsjSysUser source, SysUserEdit target) {

    }
}
