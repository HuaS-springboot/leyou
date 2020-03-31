package com.vsj.service.impl;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.dao.ProductImgDAO;
import com.vsj.model.VsjProductImg;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname ProductImageServiceImpl
 * @Description TODO
 * @Date 2019/7/29 13:41
 * @Created by wangzx
 */
@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImgDAO productImgDAO;

    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> abstractObjectConverter;
    @Override
    public Object getProductImageList(QueryStat queryStat) {
        BaseQueryStat baseQueryStat=abstractObjectConverter.convert(queryStat,BaseQueryStat.class);
        List<VsjProductImg> list = productImgDAO.getProductImageList(baseQueryStat);
        return BaseResponseParam.success(list);
    }
}
