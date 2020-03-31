package com.vsj.common.model.convert;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.request.ActivitySpecs;
import com.vsj.model.response.ActivitySpecsResponse;
import org.springframework.stereotype.Component;

/**
 * @Classname ActivitySpecsConvert
 * @Description TODO
 * @Date 2019/8/22 11:11
 * @Created by zy
 */
@Component
public class ActivitySpecsConvert extends AbstractObjectConverter<ActivitySpecs, ActivitySpecsResponse> {
    @Override
    protected void convertImpl(ActivitySpecs source, ActivitySpecsResponse target) {

        target.setActivityId(source.getActivityId());
        target.setActivityPrice(source.getActivityPrice());
        target.setAttrJson(source.getAttrJson());
        target.setCreateTime(source.getCreateTime());
        target.setGroupNumber(source.getGroupNumber());
        target.setId(source.getId());
        target.setPlatformCode(source.getPlatformCode());
        target.setProductNumber(source.getProductNumber());
        target.setProductStock(source.getProductStock());
        target.setProductPrice(source.getProductPrice());
        target.setQuotaNumber(source.getQuotaNumber());
        target.setSpecsId(source.getSpecsId());
        target.setActivityType(source.getActivityType());
    }

    @Override
    protected void reConvertImpl(ActivitySpecsResponse source, ActivitySpecs target) {

    }
}
