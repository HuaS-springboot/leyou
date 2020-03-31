package com.vsj.common.model.convert;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.request.ProductCategory;
import com.vsj.model.VsjProductCategory;
import org.springframework.stereotype.Component;

/**
 * @Author: HuaS
 * @Date :2019/8/2 12:49
 * @Describe:
 */
@Component
public class VsjProductCategoryConvert extends AbstractObjectConverter<ProductCategory, VsjProductCategory> {
    @Override
    protected void convertImpl(ProductCategory source, VsjProductCategory target) {
        target.setCategoryCode(source.getCategoryCode());
        target.setCategoryName(source.getCategoryName());
        target.setCategorySort(source.getCategorySort());
        target.setCategoryStatus(source.getCategoryStatus());
        target.setIco(source.getIco());
        target.setId(source.getId());
        target.setModifiedTime(source.getModifiedTime());
        target.setParentId(source.getParentId());
        target.setTypeNum(source.getTypeNum());
        target.setChildren(source.getChildren());
    }

    @Override
    protected void reConvertImpl(VsjProductCategory source, ProductCategory target) {

    }
}
