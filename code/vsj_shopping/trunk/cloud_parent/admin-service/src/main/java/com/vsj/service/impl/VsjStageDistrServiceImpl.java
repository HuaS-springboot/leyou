package com.vsj.service.impl;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.VsjProductRequest;
import com.vsj.common.model.request.VsjStageDistrRequest;
import com.vsj.dao.VsjStageDistrDAO;
import com.vsj.model.VsjStageDistr;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.service.ProductService;
import com.vsj.service.VsjStageDistrService;
import io.swagger.models.auth.In;
import net.bytebuddy.asm.Advice;
import org.bouncycastle.cms.PasswordRecipientId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname VsjStageDistrServiceImpl
 * @Description 分销接口实现
 * @Date 2019/8/14 14:05
 * @Created by wangzx
 */
@Service
public class VsjStageDistrServiceImpl implements VsjStageDistrService {

    @Autowired
    private VsjStageDistrDAO vsjStageDistrDAO;
    @Autowired
    private ProductService productService;
    @Autowired
    private AbstractObjectConverter<VsjStageDistrRequest, VsjStageDistr> converter;
    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> queryStatConvert;


    @Override
    public BaseResponseParam insertStageDistr(VsjStageDistrRequest vsjStageDistrRequest, Integer platformCode) {
        if (vsjStageDistrRequest == null || platformCode == null) {
            return BaseResponseParam.fail("参数错误");
        }
        // 修改商品信息
        VsjProductRequest vsjProductRequest = new VsjProductRequest();
        vsjProductRequest.setProductId(vsjStageDistrRequest.getProductId());
        vsjProductRequest.setIsCommission(vsjStageDistrRequest.getIsCommission());
        vsjProductRequest.setIsDealer(vsjStageDistrRequest.getIsDealer());
        productService.updateProduct(vsjProductRequest,platformCode);
        // 如果关闭配置,删除之前的配置
        if(vsjStageDistrRequest.getIsCommission() != null && vsjStageDistrRequest.getIsCommission() == 0){
            BaseQueryStat baseQueryStat = new BaseQueryStat();
            baseQueryStat.setPlatformCode(platformCode);
            baseQueryStat.setProductId(vsjStageDistrRequest.getProductId());
            vsjStageDistrDAO.deleteStageDistrByProductId(baseQueryStat);
        }else {
            VsjStageDistr vsjStageDistr = converter.convert(vsjStageDistrRequest, VsjStageDistr.class);
            vsjStageDistr.setPlatformCode(platformCode);
            vsjStageDistrDAO.insertStageDistr(vsjStageDistr);
        }

        return BaseResponseParam.success();
    }

    @Override
    public BaseResponseParam getStageDistrByProductId(QueryStat queryStat) {
        if (queryStat.getId() == null || queryStat.getPlatformCode() == null) {
            return BaseResponseParam.fail("参数错误");
        }
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        VsjStageDistr vsjStageDistr = vsjStageDistrDAO.getStageDistrByProductId(baseQueryStat);
        if(vsjStageDistr == null){
            baseQueryStat.setId(0);
            vsjStageDistr = vsjStageDistrDAO.getStageDistrByProductId(baseQueryStat);
            if(vsjStageDistr == null){
                vsjStageDistr = new VsjStageDistr();
            }
        }
        return BaseResponseParam.success(vsjStageDistr);
    }

    @Override
    public BaseResponseParam updateStageDistrById(VsjStageDistrRequest vsjStageDistrRequest, Integer platformCode) {
        if (vsjStageDistrRequest.getId() == null || platformCode == null) {
            return BaseResponseParam.fail("参数错误");
        }
        VsjStageDistr vsjStageDistr = converter.convert(vsjStageDistrRequest, VsjStageDistr.class);
        vsjStageDistr.setPlatformCode(platformCode);
        int count = vsjStageDistrDAO.updateStageDistrById(vsjStageDistr);
        if (count > 0) {
            return BaseResponseParam.success();
        }
        return BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam deleteStageDistrById(QueryStat queryStat) {
        if (queryStat.getId() == null || queryStat.getPlatformCode() == null) {
            return BaseResponseParam.fail("参数错误");
        }
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        int count = vsjStageDistrDAO.deleteStageDistrById(baseQueryStat);
        if(count > 0){
            return BaseResponseParam.success();
        }
        return BaseResponseParam.fail();
    }
}
