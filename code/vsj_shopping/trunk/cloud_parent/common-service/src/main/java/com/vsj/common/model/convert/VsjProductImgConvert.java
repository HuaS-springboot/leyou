package com.vsj.common.model.convert;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.request.VsjProductImgRequest;
import com.vsj.model.VsjProductImg;
import org.springframework.stereotype.Component;

/**
 * @Classname VsjProductImgConvert
 * @Description TODO
 * @Date 2019/8/2 15:05
 * @Created by wangzx
 */
@Component
public class VsjProductImgConvert extends AbstractObjectConverter<VsjProductImgRequest, VsjProductImg> {

    @Override
    protected void convertImpl(VsjProductImgRequest vsjProductImgRequest, VsjProductImg vsjProductImg) {
        vsjProductImg.setId(vsjProductImgRequest.getId());
        vsjProductImg.setProductId(vsjProductImgRequest.getProductId());
        vsjProductImg.setPicDesc(vsjProductImgRequest.getPicDesc());
        vsjProductImg.setPicUrl(vsjProductImgRequest.getPicUrl());
        vsjProductImg.setIsMaster(vsjProductImgRequest.getIsMaster());
        vsjProductImg.setPicOrder(vsjProductImgRequest.getPicOrder());
        vsjProductImg.setPicStatus(vsjProductImgRequest.getPicStatus());
    }

    @Override
    protected void reConvertImpl(VsjProductImg source, VsjProductImgRequest target) {

    }
}
