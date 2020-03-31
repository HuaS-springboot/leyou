package com.vsj.common.model.convert;

import com.alibaba.fastjson.JSON;
import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.request.MemberUpgrade;
import com.vsj.model.VsjMemberUpgradeRules;
import org.springframework.stereotype.Component;

@Component
public class MemberUpgradeConvert extends AbstractObjectConverter<MemberUpgrade, VsjMemberUpgradeRules> {
    @Override
    protected void convertImpl(MemberUpgrade source, VsjMemberUpgradeRules target) {
        target.setCurrentRelation(source.getCurrentRelation());
        target.setData(JSON.toJSONString(source.getData()));
        target.setId(source.getId());
        target.setLevelId(source.getLevelId());
        target.setParentId(source.getParentId());
        target.setParentRelation(source.getParentRelation());
        target.setType(source.getType());
    }

    @Override
    protected void reConvertImpl(VsjMemberUpgradeRules source, MemberUpgrade target) {

    }
}
