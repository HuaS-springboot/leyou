package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.ProductCategory;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.VsjProductCategoryList;
import com.vsj.model.VsjProductCategory;

import java.util.List;

public interface ProductCatetoryService {
    Object insertProductCategory(ProductCategory productCategory,Integer platformCode);

    Object updateProductCategory(ProductCategory productCategory,Integer platformCode);

    Object deleteProductCategory(ProductCategory productCategory,Integer platformCode);

    Object selectProductCategoryDetails(ProductCategory productCategory);

    BaseResponseParam getProductCategoryList(QueryStat queryStat);
}
