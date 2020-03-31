package com.vsj.service;

import com.vsj.common.model.request.QueryStat;

/**
 * @Classname ProductService
 * @Description TODO
 * @Date 2019/7/29 14:20
 * @Created by wangzx
 */
public interface ProductService {

    Object getProductList(QueryStat queryStat);

    Object getProductDetail(QueryStat queryStat);
}
