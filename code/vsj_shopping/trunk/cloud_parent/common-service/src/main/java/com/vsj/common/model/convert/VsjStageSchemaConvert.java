package com.vsj.common.model.convert;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.request.VsjStageSchemaRequest;
import com.vsj.model.VsjStageSchema;
import org.springframework.stereotype.Component;

/**
 * @Classname VsjStageSchemaConvert
 * @Description
 * @Date 2019/8/2 16:03
 * @Created by wangzx
 */
@Component
public class VsjStageSchemaConvert extends AbstractObjectConverter<VsjStageSchemaRequest, VsjStageSchema> {

    @Override
    protected void convertImpl(VsjStageSchemaRequest vsjStageSchemaRequest, VsjStageSchema vsjStageSchema) {
        vsjStageSchema.setId(vsjStageSchemaRequest.getId());
        vsjStageSchema.setLevelId(vsjStageSchemaRequest.getLevelId());
        vsjStageSchema.setOpenPeers(vsjStageSchemaRequest.getOpenPeers());
        vsjStageSchema.setBonusNum(vsjStageSchemaRequest.getBonusNum());
        vsjStageSchema.setBonusUnits(vsjStageSchemaRequest.getBonusUnits());
        vsjStageSchema.setPeersNum(vsjStageSchemaRequest.getPeersNum());
        vsjStageSchema.setPerrsUnits(vsjStageSchemaRequest.getPerrsUnits());
        vsjStageSchema.setPeersHierarchy(vsjStageSchemaRequest.getPeersHierarchy());
        vsjStageSchema.setProductId(vsjStageSchemaRequest.getProductId());
    }

    @Override
    protected void reConvertImpl(VsjStageSchema source, VsjStageSchemaRequest target) {

    }
}
