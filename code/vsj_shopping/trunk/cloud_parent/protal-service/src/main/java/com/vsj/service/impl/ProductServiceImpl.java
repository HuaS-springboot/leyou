package com.vsj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.dao.ProductDAO;
import com.vsj.model.VsjProduct;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.ProductResponse;
import com.vsj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname ProductServiceImpl
 * @Description TODO
 * @Date 2019/7/29 14:20
 * @Created by wangzx
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> abstractObjectConverter;
    @Override
    public Object getProductList(QueryStat queryStat) {
        PageHelper.startPage(queryStat.getPageNum(),queryStat.getPageSize());
        BaseQueryStat baseQueryStat=abstractObjectConverter.convert(queryStat,BaseQueryStat.class);
        List<VsjProduct> productList = productDAO.getProductList(baseQueryStat);
        PageInfo<VsjProduct> pageInfo = new PageInfo<>(productList);
        return BaseResponseParam.success(pageInfo);
    }

    @Override
    public Object getProductDetail(QueryStat queryStat) {
        if(queryStat.getId() == null){
            return BaseResponse.fail("参数错误");
        }
        BaseQueryStat baseQueryStat=abstractObjectConverter.convert(queryStat,BaseQueryStat.class);
        ProductResponse productDetail = productDAO.getProductDetail(baseQueryStat);
        return BaseResponseParam.success(productDetail);
    }
}
