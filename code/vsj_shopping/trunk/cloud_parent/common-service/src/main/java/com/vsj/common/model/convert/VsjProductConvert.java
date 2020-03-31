package com.vsj.common.model.convert;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.request.VsjProductRequest;
import com.vsj.model.VsjProduct;
import org.springframework.stereotype.Component;

/**
 * @Classname VsjProductConvert
 * @Description TODO
 * @Date 2019/8/2 10:41
 * @Created by wangzx
 */
@Component
public class VsjProductConvert extends AbstractObjectConverter<VsjProductRequest, VsjProduct> {

    @Override
    protected void convertImpl(VsjProductRequest vsjProductRequest, VsjProduct vsjProduct) {
        vsjProduct.setProductId(vsjProductRequest.getProductId());
        vsjProduct.setProductName(vsjProductRequest.getProductName());
        vsjProduct.setProductCode(vsjProductRequest.getProductCode());
        vsjProduct.setProductImage(vsjProductRequest.getProductImage());
        vsjProduct.setBrandId(vsjProductRequest.getBrandId());
        vsjProduct.setOneCategoryId(vsjProductRequest.getOneCategoryId());
        vsjProduct.setSupplierId(vsjProductRequest.getSupplierId());
        vsjProduct.setPrice(vsjProductRequest.getPrice());
        vsjProduct.setPublishStatus(vsjProductRequest.getPublishStatus());
        vsjProduct.setAuditStatus(vsjProductRequest.getAuditStatus());
        vsjProduct.setSaleNum(vsjProductRequest.getSaleNum());
        vsjProduct.setSaleTime(vsjProductRequest.getSaleTime());
        vsjProduct.setWeight(vsjProductRequest.getWeight());
        vsjProduct.setIsExchange(vsjProductRequest.getIsExchange());
        vsjProduct.setProductSort(vsjProductRequest.getProductSort());
        vsjProduct.setTwoCategoryId(vsjProductRequest.getTwoCategoryId());
        vsjProduct.setIsDealer(vsjProductRequest.getIsDealer());
        vsjProduct.setIsCommission(vsjProductRequest.getIsCommission());
        vsjProduct.setTemplateId(vsjProductRequest.getTemplateId());
        vsjProduct.setThreeCategoryId(vsjProductRequest.getThreeCategoryId());
        vsjProduct.setShareImage(vsjProductRequest.getShareImage());
        vsjProduct.setShareTitle(vsjProductRequest.getShareTitle());
        vsjProduct.setDescribesion(vsjProductRequest.getDescribesion());
    }

    @Override
    protected void reConvertImpl(VsjProduct source, VsjProductRequest target) {

    }
}
