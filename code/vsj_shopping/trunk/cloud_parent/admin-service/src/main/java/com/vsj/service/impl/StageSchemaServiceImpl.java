package com.vsj.service.impl;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.VsjProductRequest;
import com.vsj.common.model.request.VsjStageSchemaRequest;
import com.vsj.common.model.request.VsjStageSchemaRequestList;
import com.vsj.dao.StageSchemaDAO;
import com.vsj.model.VsjProduct;
import com.vsj.model.VsjStageSchema;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.service.ProductService;
import com.vsj.service.StageSchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname VsjStageSchemaServiceImpl
 * @Description TODO
 * @Date 2019/7/26 17:25
 * @Created by wangzx
 */
@Service
public class StageSchemaServiceImpl implements StageSchemaService {

    @Autowired
    private StageSchemaDAO stageSchemaDao;
    @Autowired
    private ProductService productService;
    @Autowired
    private AbstractObjectConverter<VsjStageSchemaRequest,VsjStageSchema> converter;
    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> queryStatConvert;


    @Override
    public BaseResponseParam insertStageSchema(VsjStageSchemaRequestList vsjStageSchemaRequestList, Integer platformCode) {
        List<VsjStageSchemaRequest> schemaRequestList = vsjStageSchemaRequestList.getSchemaRequestList();
        if(schemaRequestList.isEmpty()){
            return BaseResponseParam.fail("参数错误");
        }
        List<VsjStageSchema> list = new ArrayList<>();
        for (VsjStageSchemaRequest vsjStageSchemaRequest : schemaRequestList) {
            VsjStageSchema vsjStageSchema = converter.convert(vsjStageSchemaRequest, VsjStageSchema.class);
            vsjStageSchema.setPlatformCode(platformCode);
            // 插入StageSchema数据
            list.add(vsjStageSchema);
        }
        stageSchemaDao.insertStageSchemaBatch(list);
        if(vsjStageSchemaRequestList.getProductId() != null){
            VsjProductRequest vsjProductRequest = new VsjProductRequest();
            vsjProductRequest.setProductId(vsjStageSchemaRequestList.getProductId());
            vsjProductRequest.setIsDealer(vsjStageSchemaRequestList.getIsDealer());
            vsjProductRequest.setIsCommission(vsjStageSchemaRequestList.getIsCommission());
            productService.updateProduct(vsjProductRequest,platformCode);
            if(vsjStageSchemaRequestList.getIsCommission() != null && vsjStageSchemaRequestList.getIsCommission() != 0){
                // 如果关闭独立提成开关,删除之前的所有配置
                BaseQueryStat baseQueryStat = new BaseQueryStat();
                baseQueryStat.setProductId(vsjStageSchemaRequestList.getProductId());
                baseQueryStat.setPlatformCode(platformCode);
                stageSchemaDao.deleteStageSchemaByProductId(baseQueryStat);
            }
        }
        return BaseResponseParam.success();
    }

    @Override
    public BaseResponseParam updateStageSchema(VsjProductRequest vsjProductRequest, VsjStageSchemaRequest vsjStageSchemaRequest,Integer platformCode) {
        if(vsjProductRequest.getProductId() == null){
            return BaseResponseParam.fail("参数错误");
        }
        VsjStageSchema vsjStageSchema = converter.convert(vsjStageSchemaRequest, VsjStageSchema.class);
        vsjStageSchema.setProductId(platformCode);
        // 修改数据
        stageSchemaDao.updateStageSchema(vsjStageSchema);
        // 更新商品的开关状态
        productService.updateProduct(vsjProductRequest,platformCode);
        return BaseResponseParam.success();
    }

    @Override
    public BaseResponseParam deleteStageSchema(QueryStat queryStat) {
        if(queryStat.getId() != null){
            return BaseResponseParam.fail("参数错误");
        }
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        int count = stageSchemaDao.deleteStageSchema(baseQueryStat);
        if(count > 0){
            return BaseResponseParam.success();
        }
        return BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam getStageSchemaList(QueryStat queryStat) {
    	List<VsjStageSchema> vsjStageSchemaList = stageSchemaDao.selectStageSchema(queryStat.getProductId(), queryStat.getPlatformCode());
    	boolean flag = false;
    	// 如果没有就查询系统的配置
        for (VsjStageSchema vsjStageSchema : vsjStageSchemaList) {
            if(vsjStageSchema.getProductId() != null){
                // 只要查询出的集合中只要有一个有productId，证明已经配置了
                flag = true;
            }
        }
        // 如果都没productId,证明该商品没有配置奖励模式,返回系统配置
        if(!flag){
            vsjStageSchemaList = stageSchemaDao.selectStageSchema(0, queryStat.getPlatformCode());
        }
    	return BaseResponseParam.success(vsjStageSchemaList);
    }
}
