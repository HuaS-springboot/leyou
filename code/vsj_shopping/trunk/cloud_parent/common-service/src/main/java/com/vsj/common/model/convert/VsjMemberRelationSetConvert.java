package com.vsj.common.model.convert;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.request.VsjMemberRelation;
import com.vsj.model.VsjMemberRelationSet;
import org.springframework.stereotype.Component;

@Component
public class VsjMemberRelationSetConvert extends AbstractObjectConverter<VsjMemberRelation, VsjMemberRelationSet> {

    @Override
    protected void convertImpl(VsjMemberRelation source, VsjMemberRelationSet target) {
        target.setId(source.getId());
        target.setBindPhone(source.getBindPhone());
        target.setExpenseMoney(source.getExpenseMoney());
        target.setExpenseNum(source.getExpenseNum());
        target.setNeedRequire(source.getNeedRequire());
        target.setOfflineConditions(source.getOfflineConditions());
        target.setProductIds(source.getProductIds());
        target.setProductNum(source.getProductNum());
        target.setRelationSwitch(source.getRelationSwitch());
        target.setPlatformCode(source.getPlatformCode());
    }

    @Override
    protected void reConvertImpl(VsjMemberRelationSet source, VsjMemberRelation target) {

    }
}
