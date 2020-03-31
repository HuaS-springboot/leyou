package com.vsj.material.model.convert;

import com.vsj.material.model.MaterialCategroyGuide;
import com.vsj.material.model.request.MaterialCategroyGuideRequest;
import org.springframework.stereotype.Component;

/**
 * @Classname MaterialCategroyGuideConverter
 * @Description 分类导航转换类
 * @Date 2019/8/16 19:45
 * @Created by sxm
 */
@Component
public class MaterialCategroyGuideConverter extends AbstractObjectConverter<MaterialCategroyGuideRequest, MaterialCategroyGuide> {

    @Override
    protected void convertImpl(MaterialCategroyGuideRequest source, MaterialCategroyGuide target) {
        target.setCategoryId(source.getCategoryId());
        target.setId(source.getId());
        target.setImageUrl(source.getImageUrl());
    }

    @Override
    protected void reConvertImpl(MaterialCategroyGuide source, MaterialCategroyGuideRequest target) {

    }


}
