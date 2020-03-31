package com.vsj.common.model.convert;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.request.VsjProductAttrValueRequest;
import com.vsj.model.VsjProductAttrValue;
import org.springframework.stereotype.Component;

/**
 * @Classname VsjProductAttrValueConvert
 * @Description TODO
 * @Date 2019/8/2 15:33
 * @Created by wangzx
 */
@Component
public class VsjProductAttrValueConvert extends AbstractObjectConverter<VsjProductAttrValueRequest, VsjProductAttrValue> {

    @Override
    protected void convertImpl(VsjProductAttrValueRequest vsjProductAttrValueRequest, VsjProductAttrValue vsjProductAttrValue) {
        vsjProductAttrValue.setAttrValue(vsjProductAttrValueRequest.getAttrValue());
        vsjProductAttrValue.setValueSort(vsjProductAttrValueRequest.getValueSort());
    }

    @Override
    protected void reConvertImpl(VsjProductAttrValue source, VsjProductAttrValueRequest target) {

    }
}
