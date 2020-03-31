package com.vsj.service.impl;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.dao.ProductCategoryDAO;
import com.vsj.model.VsjProductCategory;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname ProductCategoryServiceImpl
 * @Description TODO
 * @Date 2019/7/29 15:24
 * @Created by wangzx
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDAO productCategoryDAO;

    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> abstractObjectConverter;
    @Override
    public Object getProductCategoryList(QueryStat queryStat) {
        BaseQueryStat baseQueryStat=abstractObjectConverter.convert(queryStat,BaseQueryStat.class);
        List<VsjProductCategory> productCategoryList = productCategoryDAO.getProductCategoryList(baseQueryStat);//getProductCategoryList();
        return BaseResponseParam.success(productCategoryList);
    }

}
