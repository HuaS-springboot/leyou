package com.vsj.material.model.convert;

import com.vsj.material.model.MaterialMemberPackage;
import com.vsj.material.model.request.MaterialMemberPackageRequest;
import lombok.Data;
import org.springframework.stereotype.Component;



@Component
public class MaterialMemberPackageConvert extends AbstractObjectConverter<MaterialMemberPackageRequest, MaterialMemberPackage> {

    @Override
    protected void convertImpl(MaterialMemberPackageRequest source, MaterialMemberPackage target) {
        target.setId(source.getId());
        target.setDay(source.getDay());
        target.setLevelId(source.getLevelId());
        target.setStatus(source.getStatus());
        target.setMoney(source.getMoney());
    }

    @Override
    protected void reConvertImpl(MaterialMemberPackage source, MaterialMemberPackageRequest target) {

    }
}
