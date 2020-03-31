package com.vsj.common.model.convert;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.request.VsjProductExtensionRequest;
import com.vsj.model.VsjProductExtension;
import org.springframework.stereotype.Component;

/**
 * @Classname VsjProductExtensionConvert
 * @Description TODO
 * @Date 2019/8/2 14:12
 * @Created by wangzx
 */
@Component
public class VsjProductExtensionConvert extends AbstractObjectConverter<VsjProductExtensionRequest, VsjProductExtension> {

    @Override
    protected void convertImpl(VsjProductExtensionRequest vsjProductExtensionRequest, VsjProductExtension vsjProductExtension) {
        vsjProductExtension.setId(vsjProductExtensionRequest.getId());
        vsjProductExtension.setProductId(vsjProductExtensionRequest.getProductId());
        vsjProductExtension.setType(vsjProductExtensionRequest.getType());
        vsjProductExtension.setExtensionKey(vsjProductExtensionRequest.getExtensionKey());
        vsjProductExtension.setExtensionValue(vsjProductExtensionRequest.getExtensionValue());
    }

    @Override
    protected void reConvertImpl(VsjProductExtension source, VsjProductExtensionRequest target) {

    }
}
