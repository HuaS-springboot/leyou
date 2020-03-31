package com.vsj.common.model.convert;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.request.MaterialL;
import com.vsj.model.VsjMaterial;
import org.springframework.stereotype.Component;

@Component
public class VsjMaterialConvent extends AbstractObjectConverter<MaterialL, VsjMaterial> {
    @Override
    protected void convertImpl(MaterialL source, VsjMaterial target) {
        target.setImg(source.getImg());
        target.setContent(source.getContent());
        target.setId(source.getId());
        target.setProductId(source.getProductId());
        target.setSort(source.getSort());
        target.setPlatformCode(source.getPlatformCode());
        target.setCreateTime(source.getCreateTime());
//        target.setIds(source.getIds());
//        target.setSorts(source.getSorts());
    }

    @Override
    protected void reConvertImpl(VsjMaterial source, MaterialL target) {

    }
}
