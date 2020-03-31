package com.vsj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.VsjProductExtensionRequest;
import com.vsj.common.model.request.VsjProductExtensionRequestList;
import com.vsj.dao.ProductExtensionDAO;
import com.vsj.model.VsjProductExtension;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.service.ProductExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname ProductExtensionServiceImpl
 * @Description TODO
 * @Date 2019/7/24 18:31
 * @Created by wangzx
 */
@Service
public class ProductExtensionServiceImpl implements ProductExtensionService {

    @Autowired
    private ProductExtensionDAO ProductExtensionDao;
    @Autowired
    private AbstractObjectConverter<VsjProductExtensionRequest, VsjProductExtension> converter;
    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> queryStatConvert;


    @Override
    public BaseResponseParam insertProductExtension(VsjProductExtensionRequestList vsjProductExtensionRequestList, Integer platformCode) {
        List<VsjProductExtensionRequest> productExtensionList = vsjProductExtensionRequestList.getProductExtensionList();
        if (productExtensionList.isEmpty() || platformCode == null) {
            return BaseResponseParam.fail("参数错误");
        }
        int count = 0;
        for (VsjProductExtensionRequest vsjProductExtensionRequest : productExtensionList) {
            VsjProductExtension vsjProductExtension = converter.convert(vsjProductExtensionRequest, VsjProductExtension.class);
            vsjProductExtension.setPlatformCode(platformCode);
            count += ProductExtensionDao.insertProductExtension(vsjProductExtension);
        }
        if (count > 0) {
            return BaseResponseParam.success();
        }
        return BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam getExtensionList(QueryStat queryStat) {
        PageHelper.startPage(queryStat.getPageNum(), queryStat.getPageSize());
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat, BaseQueryStat.class);
        List<VsjProductExtension> list = ProductExtensionDao.getExtensionList(baseQueryStat);
        PageInfo<VsjProductExtension> pageInfo = new PageInfo<>(list);
        return BaseResponseParam.success(pageInfo);
    }

    @Override
    public BaseResponseParam updateExtension(VsjProductExtensionRequestList vsjProductExtensionRequestList, Integer platformCode) {
        List<VsjProductExtensionRequest> productExtensionList = vsjProductExtensionRequestList.getProductExtensionList();
        if (productExtensionList.isEmpty()) {
            return BaseResponseParam.fail("参数错误");
        }
        for (VsjProductExtensionRequest vsjProductExtensionRequest : productExtensionList) {
            VsjProductExtension vsjProductExtension = converter.convert(vsjProductExtensionRequest, VsjProductExtension.class);
            vsjProductExtension.setPlatformCode(platformCode);
            if (vsjProductExtension.getId() == null) {
                return BaseResponseParam.fail("参数错误");
            }
            ProductExtensionDao.updateExtension(vsjProductExtension);
        }
        return BaseResponseParam.success();
    }

    @Override
    public BaseResponseParam deleteExtension(QueryStat queryStat) {
        if (queryStat.getId() == null) {
            return BaseResponseParam.fail("参数错误");
        }
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat, BaseQueryStat.class);
        int count = ProductExtensionDao.deleteExtension(baseQueryStat);
        if (count > 0) {
            return BaseResponseParam.success();
        }
        return BaseResponseParam.fail();
    }
}
