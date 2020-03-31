package com.vsj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.ProductImgRequestList;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.VsjProductImgRequest;
import com.vsj.dao.ProductImgDAO;
import com.vsj.model.VsjProductImg;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.service.ProductImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname ProductImgService
 * @Description TODO
 * @Date 2019/7/29 9:28
 * @Created by wangzx
 */
@Service
public class ProductImgServiceImpl implements ProductImgService {

    @Autowired
    private ProductImgDAO productImgDao;
    @Autowired
    private AbstractObjectConverter<VsjProductImgRequest,VsjProductImg> converter;
    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> queryStatConvert;

    @Override
    public BaseResponseParam insertProductImage(ProductImgRequestList productImgRequestList, Integer platformCode) {
        List<VsjProductImgRequest> imgRequestList = productImgRequestList.getImgRequestList();
        if(imgRequestList.isEmpty() || platformCode == null){
            return BaseResponseParam.fail("参数错误");
        }
        imgRequestList.stream().forEach(productImgRequest ->{
            // 查询该商品id是否存在
            VsjProductImg priorImage = productImgDao.getProductImageByProductId(productImgRequest.getProductId(),platformCode);
            // 如果存在,则在地址后面再拼接
            if(priorImage != null){
                StringBuffer stringBuffer = new StringBuffer();
                String newUrl = stringBuffer.append(priorImage.getPicUrl()).append(",").append(productImgRequest.getPicUrl()).toString();
                // 修改地址
                priorImage.setPicUrl(newUrl);
                productImgDao.updateProductImage(priorImage);
            }else {
                VsjProductImg productImg = converter.convert(productImgRequest, VsjProductImg.class);
                productImg.setPlatformCode(platformCode);
                int count = productImgDao.insertProductImage(productImg);
            }
        });
        return BaseResponseParam.success();
    }

    @Override
    public BaseResponseParam getProductImageList(QueryStat queryStat) {
        PageHelper.startPage(queryStat.getPageNum(),queryStat.getPageSize());
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        List<VsjProductImg> list = productImgDao.getProductImageList(baseQueryStat);
        PageInfo<VsjProductImg> pageInfo = new PageInfo<>(list);
        return BaseResponseParam.success(pageInfo);
    }

    @Override
    public BaseResponseParam updateProductImage(ProductImgRequestList productImgRequestList,Integer platformCode) {
        List<VsjProductImgRequest> imgRequestList = productImgRequestList.getImgRequestList();
        if(imgRequestList.isEmpty()){
            // 删掉所有
            productImgDao.deleteProductImageByType(productImgRequestList.getType(),platformCode);
            return BaseResponseParam.success();
        }
        for (VsjProductImgRequest vsjProductImgRequest : imgRequestList) {
            if(vsjProductImgRequest.getId() == null){
                return BaseResponseParam.fail("参数错误");
            }
            VsjProductImg vsjProductImg = converter.convert(vsjProductImgRequest, VsjProductImg.class);
            vsjProductImg.setPlatformCode(platformCode);
            int count = productImgDao.updateProductImage(vsjProductImg);
        }
        return BaseResponseParam.success();
    }

    @Override
    public BaseResponseParam deleteProductImage(QueryStat queryStat) {
        if(queryStat.getId() == null){
            return BaseResponseParam.fail("参数错误");
        }
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        int count = productImgDao.deleteProductImage(baseQueryStat);
        if(count > 0){
            return BaseResponseParam.success();
        }
        return BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam getMasteImage(QueryStat queryStat) {
        List<VsjProductImg> vsjProductImg = productImgDao.getMasteImage(queryStat.getProductId(),queryStat.getPlatformCode());
        return BaseResponseParam.success(vsjProductImg);
    }
}
