package com.vsj.common.model.convert;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.request.MemberLevel;
import com.vsj.model.VsjMemberLevel;
import org.springframework.stereotype.Component;

@Component
public class VsjMemberLevelConvert extends AbstractObjectConverter<MemberLevel, VsjMemberLevel> {
    @Override
    protected void convertImpl(MemberLevel source, VsjMemberLevel target) {
        if(null != source.getId()){
            target.setId(source.getId());
        }
        target.setIsDefault(source.getIsDefault());
        target.setLevelName(source.getLevelName());
        target.setSort(source.getSort());
        target.setPlatformCode(source.getPlatformCode());
    }

    @Override
    protected void reConvertImpl(VsjMemberLevel source, MemberLevel target) {

    }
}
