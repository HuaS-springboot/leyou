package com.vsj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.SysConfig;
import com.vsj.common.model.request.VsjProductList;
import com.vsj.common.model.request.VsjProductRequest;
import com.vsj.common.utils.StringUtil;
import com.vsj.dao.ProductDAO;
import com.vsj.model.VsjProduct;
import com.vsj.model.VsjSysConfig;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.ProductResponse;
import com.vsj.service.ProductService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname ProductServiceImpl
 * @Description 商品相关接口实现
 * @Date 2019/7/23 16:38
 * @Created by wangzx
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDao;
    @Autowired
    private AbstractObjectConverter<VsjProductRequest,VsjProduct> converter;
    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> queryStatConvert;

    @Override
    public BaseResponseParam insertProduct(VsjProductRequest vsjProductRequest,Integer platformCode) {
        VsjProduct vsjProduct = converter.convert(vsjProductRequest, VsjProduct.class);
        vsjProduct.setPlatformCode(platformCode);
        int count = productDao.insertProduct(vsjProduct);
        if(count > 0){
            return BaseResponseParam.success(vsjProduct);
        }
        return BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam getProductList(QueryStat queryStat) {
        PageHelper.startPage(queryStat.getPageNum(),queryStat.getPageSize());
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        List<VsjProduct> list = productDao.getProductList(baseQueryStat);
        PageInfo<VsjProduct> pageInfo = new PageInfo<>(list);
        return BaseResponseParam.success(pageInfo);
    }

    @Override
    public BaseResponseParam updateProStatus(QueryStat queryStat) {
        if(StringUtil.isEmptyStr(queryStat.getIds()) || queryStat.getStatus() == null){
            return BaseResponseParam.fail("参数错误");
        }
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        int count = productDao.updateProStatus(baseQueryStat);
        if(count > 0){
            return BaseResponseParam.success();
        }
        return BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam getProductDetail(QueryStat queryStat) {
        if(queryStat.getId() == null || queryStat.getPlatformCode() == null){
            return BaseResponseParam.fail("参数错误");
        }
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        ProductResponse response = productDao.getProductDetail(baseQueryStat);
        return BaseResponseParam.success(response);
    }

    @Override
    public BaseResponseParam updateProduct(VsjProductRequest vsjProductRequest,Integer platformCode) {
        VsjProduct vsjProduct = converter.convert(vsjProductRequest, VsjProduct.class);
        vsjProduct.setPlatformCode(platformCode);
        if(vsjProduct.getProductId() == null || vsjProduct.getPlatformCode() == null){
            return BaseResponseParam.fail("参数错误");
        }
        int count = productDao.updateProduct(vsjProduct);
        if(count > 0){
            return BaseResponseParam.success();
        }
        return BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam updateProductBatch(VsjProductList vsjProductList,Integer platformCode) {
        List<VsjProductRequest> productList = vsjProductList.getProductList();
        if(productList.isEmpty()){
            return BaseResponseParam.fail("参数错误");
        }
        for (VsjProductRequest vsjProductRequest : productList) {
            VsjProduct vsjProduct = converter.convert(vsjProductRequest, VsjProduct.class);
            vsjProduct.setPlatformCode(platformCode);
            productDao.updateProduct(vsjProduct);
        }
        return BaseResponseParam.success();
    }
}
