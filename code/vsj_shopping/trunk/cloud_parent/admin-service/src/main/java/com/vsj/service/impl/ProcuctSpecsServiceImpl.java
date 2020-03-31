package com.vsj.service.impl;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.ProductSpecsList;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.VsjProductSpecsRequest;
import com.vsj.dao.ProductSpecsDAO;
import com.vsj.model.VsjProductSpecs;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.service.ProductSpecsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname ProcuctSpecsServiceImpl
 * @Description TODO
 * @Date 2019/7/26 14:08
 * @Created by wangzx
 */
@Service
public class ProcuctSpecsServiceImpl implements ProductSpecsService {

    @Autowired
    private ProductSpecsDAO productSpecsDao;
    @Autowired
    private AbstractObjectConverter<VsjProductSpecsRequest,VsjProductSpecs> converter;
    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> queryStatConvert;

    @Override
    public BaseResponseParam insertProductSpecs(ProductSpecsList productSpecsList) {
        List<VsjProductSpecsRequest> specsRequestList = productSpecsList.getSpecsList();
        //参数验空
        if(specsRequestList.isEmpty()){
            return BaseResponseParam.fail("参数错误");
        }
        Integer productId = specsRequestList.get(0).getProductId();
        List<VsjProductSpecs> list = productSpecsDao.getProductSpecsList(productId);
        if(list.isEmpty()){
            specsRequestList.stream().forEach(vsjProductSpecsRequest->{
                VsjProductSpecs vsjProductSpecs = converter.convert(vsjProductSpecsRequest, VsjProductSpecs.class);
                vsjProductSpecs.setPlatformCode(productSpecsList.getPlatformCode());
                productSpecsDao.insertProductSpecs(vsjProductSpecs);
            });
        }else {
            productSpecsDao.deleteProductSpecsByProductId(productId,productSpecsList.getPlatformCode());
            specsRequestList.stream().forEach(vsjProductSpecsRequest->{
                VsjProductSpecs vsjProductSpecs = converter.convert(vsjProductSpecsRequest, VsjProductSpecs.class);
                vsjProductSpecs.setPlatformCode(productSpecsList.getPlatformCode());
                productSpecsDao.insertProductSpecs(vsjProductSpecs);
            });
        }
        return BaseResponseParam.success();
    }

    @Override
    public BaseResponseParam updateProductSpecs(ProductSpecsList productSpecsList,Integer platformCode) {
        List<VsjProductSpecsRequest> specsList = productSpecsList.getSpecsList();
        if(specsList.isEmpty()){
            return BaseResponseParam.fail("参数错误");
        }
        for (VsjProductSpecsRequest vsjProductSpecsRequest : specsList) {
            VsjProductSpecs vsjProductSpecs = converter.convert(vsjProductSpecsRequest, VsjProductSpecs.class);
            vsjProductSpecs.setPlatformCode(platformCode);
            if(vsjProductSpecs.getId() == null){
                return BaseResponseParam.fail("参数错误");
            }
            productSpecsDao.updateProductSpecs(vsjProductSpecs);
        }
        return BaseResponseParam.success();
    }

    @Override
    public BaseResponseParam deleteProductSpecs(QueryStat queryStat) {
        if(queryStat.getId() == null){
            return BaseResponseParam.fail("参数错误");
        }
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        int count = productSpecsDao.deleteProductSpecs(baseQueryStat);
        if(count > 0){
            return BaseResponseParam.success();
        }
        return BaseResponseParam.fail();
    }
}
