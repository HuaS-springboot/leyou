package com.vsj.material.model.convert;

import com.vsj.material.model.VsjMaterialRegister;
import com.vsj.material.model.request.VsjMaterialRegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class VsjMaterialRegisterConverter extends AbstractObjectConverter<VsjMaterialRegisterRequest, VsjMaterialRegister> {

    @Override
    protected void convertImpl(VsjMaterialRegisterRequest source, VsjMaterialRegister target) {
        target.setId(source.getId());
        target.setCreateTime(source.getCreateTime());
        target.setHeadUrl(source.getHeadUrl());
        target.setNickName(source.getNickName());
        target.setArea(source.getArea());
        target.setFreezeBalance(source.getFreezeBalance());
        target.setCarryBalance(source.getCarryBalance());
        target.setCity(source.getCity());
        target.setLanguage(source.getLanguage());
        target.setCountry(source.getCountry());
        target.setOpenId(source.getOpenId());
        target.setProvince(source.getProvince());
        target.setSex(source.getSex());
        target.setTelPhone(source.getTelPhone());
        target.setType(source.getType());
    }

    @Override
    protected void reConvertImpl(VsjMaterialRegister source, VsjMaterialRegisterRequest target) {

    }
}
