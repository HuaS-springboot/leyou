package com.vsj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.ProAttrList;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.VsjProductAttrValueRequest;
import com.vsj.dao.ProductAttrKeyDAO;
import com.vsj.model.VsjProductAttrKey;
import com.vsj.model.VsjProductAttrValue;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.ProductAttrResponse;
import com.vsj.service.ProductAttrKeyService;
import com.vsj.service.ProductAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Classname ProductAttrKeyImpl
 * @Description TODO
 * @Date 2019/7/25 17:37
 * @Created by wangzx
 */
@Service
public class ProductAttrKeyServiceImpl implements ProductAttrKeyService {

    @Autowired
    private ProductAttrKeyDAO productAttrKeyDao;
    @Autowired
    private ProductAttrValueService productAttrValueService;
    @Autowired
    private AbstractObjectConverter<VsjProductAttrValueRequest, VsjProductAttrValue> converter;
    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> queryStatConvert;


    @Override
    public BaseResponseParam insertProductAttrKey(ProAttrList proAttrList) {
        VsjProductAttrKey vsjProductAttrKey = new VsjProductAttrKey();
        vsjProductAttrKey.setAttrName(proAttrList.getAttrName());
        vsjProductAttrKey.setNameSort(proAttrList.getNameSort());
        vsjProductAttrKey.setPlatformCode(proAttrList.getPlatformCode());
        int count = productAttrKeyDao.insertProductAttrKey(vsjProductAttrKey);
        if(count > 0){
            proAttrList.getAttrValueList().stream().forEach(valueRequest ->{
                VsjProductAttrValue attrValue = converter.convert(valueRequest, VsjProductAttrValue.class);
                attrValue.setPlatformCode(proAttrList.getPlatformCode());
                attrValue.setAttrId(vsjProductAttrKey.getId());
                productAttrValueService.insertAttrValue(attrValue);
            });
        }
        return BaseResponseParam.success();
    }

    @Override
    public BaseResponseParam getAttrList(QueryStat queryStat) {
        PageHelper.startPage(queryStat.getPageNum(),queryStat.getPageSize());
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        List<ProductAttrResponse> list = productAttrKeyDao.getAttrList(baseQueryStat);
        PageInfo<ProductAttrResponse> pageInfo = new PageInfo<>(list);
        return BaseResponseParam.success(pageInfo);
    }
}
