package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.VsjProductSpecsRequest;
import com.vsj.common.model.request.ProductSpecsList;
import com.vsj.common.model.request.QueryStat;

/**
 * @Classname ProductSpecsService
 * @Description TODO
 * @Date 2019/7/26 14:08
 * @Created by wangzx
 */
public interface ProductSpecsService {

    BaseResponseParam insertProductSpecs(ProductSpecsList productSpecsList);

    BaseResponseParam updateProductSpecs(ProductSpecsList productSpecsList,Integer platformCode);

    BaseResponseParam deleteProductSpecs(QueryStat queryStat);
}
