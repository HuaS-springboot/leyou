package com.vsj.material.model.convert;

import com.vsj.material.model.VsjMaterialCategory;
import com.vsj.material.model.request.MaterialCategory;
import org.springframework.stereotype.Component;

@Component
public class MaterialCategoryConverter extends AbstractObjectConverter <MaterialCategory, VsjMaterialCategory> {

    @Override
    protected void convertImpl(MaterialCategory source, VsjMaterialCategory target) {
        target.setCateName(source.getCateName());
        target.setCreateTime(source.getCreateTime());
        target.setId(source.getId());
        target.setParentId(source.getParentId());
        target.setPlatformCode(source.getPlatformCode());
        target.setSort(source.getSort());
        target.setChildren(source.getChildren());
    }

    @Override
    protected void reConvertImpl(VsjMaterialCategory source, MaterialCategory target) {

    }
}
