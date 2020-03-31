package com.vsj.common.model.convert;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.request.VsjProductSpecsRequest;
import com.vsj.model.VsjProductSpecs;
import org.springframework.stereotype.Component;

/**
 * @Classname VsjProductSpecsConvert
 * @Description TODO
 * @Date 2019/8/2 14:55
 * @Created by wangzx
 */
@Component
public class VsjProductSpecsConvert extends AbstractObjectConverter<VsjProductSpecsRequest, VsjProductSpecs> {

    @Override
    protected void convertImpl(VsjProductSpecsRequest VsjProductSpecsRequest, VsjProductSpecs VsjProductSpecs) {
        VsjProductSpecs.setId(VsjProductSpecsRequest.getId());
        VsjProductSpecs.setProductId(VsjProductSpecsRequest.getProductId());
        VsjProductSpecs.setProductStock(VsjProductSpecsRequest.getProductStock());
        VsjProductSpecs.setProductPrice(VsjProductSpecsRequest.getProductPrice());
        VsjProductSpecs.setAttrJson(VsjProductSpecsRequest.getAttrJson());
        VsjProductSpecs.setIsnullSell(VsjProductSpecsRequest.getIsnullSell());
        VsjProductSpecs.setSaleNum(VsjProductSpecsRequest.getSaleNum());
    }

    @Override
    protected void reConvertImpl(VsjProductSpecs source, VsjProductSpecsRequest target) {

    }
}
