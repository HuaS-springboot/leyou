package com.vsj.material.model.convert;

import com.vsj.material.model.MaterialConversion;
import com.vsj.material.model.request.MaterialConversionRequest;
import org.springframework.stereotype.Component;

/**
 * @Classname MaterialConversionConvert
 * @Description 兑换码转换类
 * @Date 2019/8/16 10:45
 * @Created by wangzx
 */
@Component
public class MaterialConversionConvert extends AbstractObjectConverter<MaterialConversionRequest, MaterialConversion> {
    @Override
    protected void convertImpl(MaterialConversionRequest source, MaterialConversion target) {
        target.setLevelId(source.getLevelId());
        target.setEffectiveTime(source.getEffectiveTime());
        target.setInvalidTime(source.getInvalidTime());
        target.setLevelDay(source.getLevelDay());
    }

    @Override
    protected void reConvertImpl(MaterialConversion source, MaterialConversionRequest target) {

    }
}
