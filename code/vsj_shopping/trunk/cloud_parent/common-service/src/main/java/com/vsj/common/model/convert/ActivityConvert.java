package com.vsj.common.model.convert;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.request.Activity;
import com.vsj.model.response.ActivityResponse;
import org.springframework.stereotype.Component;

@Component
public class ActivityConvert extends AbstractObjectConverter<Activity, ActivityResponse> {

    @Override
    protected void convertImpl(Activity source, ActivityResponse target) {
        target.setActivityName(source.getActivityName());
        target.setActivityType(source.getActivityType());
        target.setEndTime(source.getEndTime());
        target.setId(source.getId());
        target.setPlatformCode(source.getPlatformCode());
        target.setProductId(source.getProductId());
        target.setNickName(source.getNickName());
        target.setProductName(source.getProductName());
        target.setSysUserId(source.getSysUserId());
        target.setStartTime(source.getStartTime());
        target.setActivityStatus(source.getActivityStatus());
        target.setParticipateNumber(source.getParticipateNumber());
        target.setVsjActivityGroup(source.getVsjActivityGroup());
        target.setVsjActivitySeckills(source.getVsjActivitySeckills());
    }

    @Override
    protected void reConvertImpl(ActivityResponse source, Activity target) {

    }
}
