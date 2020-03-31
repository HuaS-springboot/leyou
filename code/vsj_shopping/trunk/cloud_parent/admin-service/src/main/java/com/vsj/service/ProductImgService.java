package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.ProductImgRequestList;
import com.vsj.common.model.request.QueryStat;

/**
 * @Classname ProductImgService
 * @Description TODO
 * @Date 2019/7/29 9:28
 * @Created by wangzx
 */
public interface ProductImgService {

    BaseResponseParam insertProductImage(ProductImgRequestList productImgRequestList, Integer platformCode);

    BaseResponseParam getProductImageList(QueryStat queryStat);

    BaseResponseParam updateProductImage(ProductImgRequestList productImgRequestList,Integer platformCode);

    BaseResponseParam deleteProductImage(QueryStat queryStat);

    BaseResponseParam getMasteImage(QueryStat queryStat);
}
