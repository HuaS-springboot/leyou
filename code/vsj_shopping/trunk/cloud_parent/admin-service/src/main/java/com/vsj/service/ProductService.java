package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.VsjProductList;
import com.vsj.common.model.request.VsjProductRequest;

/**
 * @Classname ProductService
 * @Description TODO
 * @Date 2019/7/23 16:32
 * @Created by wangzx
 */
public interface ProductService {
    BaseResponseParam insertProduct(VsjProductRequest vsjProductRequest, Integer platformCode);

    BaseResponseParam getProductList(QueryStat queryStat);

    BaseResponseParam updateProStatus(QueryStat queryStat);

    BaseResponseParam getProductDetail(QueryStat queryStat);

    BaseResponseParam updateProduct(VsjProductRequest vsjProductRequest,Integer platformCode);

    BaseResponseParam updateProductBatch(VsjProductList vsjProductList,Integer platformCode);
}
