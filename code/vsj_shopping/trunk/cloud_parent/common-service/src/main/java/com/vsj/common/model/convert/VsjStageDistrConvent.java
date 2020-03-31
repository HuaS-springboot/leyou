package com.vsj.common.model.convert;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.request.VsjStageDistrRequest;
import com.vsj.model.VsjStageDistr;
import org.springframework.stereotype.Component;

/**
 * @Classname VsjStageDistrConvent
 * @Description 分销模式转换类
 * @Date 2019/8/14 14:16
 * @Created by wangzx
 */
@Component
public class VsjStageDistrConvent extends AbstractObjectConverter<VsjStageDistrRequest, VsjStageDistr> {
    @Override
    protected void convertImpl(VsjStageDistrRequest source, VsjStageDistr target) {
        target.setId(source.getId());
        target.setLevel(source.getLevel());
        target.setFirstLevelModel(source.getFirstLevelModel());
        target.setFirstLevelValue(source.getFirstLevelValue());
        target.setSecondLevelModel(source.getSecondLevelModel());
        target.setSecondLevelValue(source.getSecondLevelValue());
        target.setProductId(source.getProductId());
    }

    @Override
    protected void reConvertImpl(VsjStageDistr source, VsjStageDistrRequest target) {

    }
}
