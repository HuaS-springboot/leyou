package com.vsj.common.model.convert;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.request.VsjSysDistTemplateRequest;
import com.vsj.model.VsjSysDistTemplate;
import org.springframework.stereotype.Component;

/**
 * @Classname VsjSysDistTemplateConvert
 * @Description TODO
 * @Date 2019/8/2 14:27
 * @Created by wangzx
 */
@Component
public class VsjSysDistTemplateConvert extends AbstractObjectConverter<VsjSysDistTemplateRequest, VsjSysDistTemplate> {

    @Override
    protected void convertImpl(VsjSysDistTemplateRequest vsjSysDistTemplateRequest, VsjSysDistTemplate vsjSysDistTemplate) {
        vsjSysDistTemplate.setId(vsjSysDistTemplateRequest.getId());
        vsjSysDistTemplate.setConfiguration(vsjSysDistTemplateRequest.getConfiguration());
    }

    @Override
    protected void reConvertImpl(VsjSysDistTemplate source, VsjSysDistTemplateRequest target) {

    }
}
