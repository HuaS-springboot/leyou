package com.vsj.material.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.utils.StringUtil;
import com.vsj.common.utils.UUIDGeneratorUtils;
import com.vsj.material.dao.MaterialConversionDAO;
import com.vsj.material.model.MaterialConversion;
import com.vsj.material.model.convert.AbstractObjectConverter;
import com.vsj.material.model.request.MaterialConversionRequest;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.model.response.MaterialConversionResponse;
import com.vsj.material.service.MaterialConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname MaterialConversionServiceImpl
 * @Description 兑换码接口实现类
 * @Date 2019/8/16 10:26
 * @Created by wangzx
 */
@Service
public class MaterialConversionServiceImpl implements MaterialConversionService {

    @Autowired
    private AbstractObjectConverter<MaterialConversionRequest, MaterialConversion> converter;
    @Autowired
    private MaterialConversionDAO materialConversionDAO;

    @Override
    public BaseResponseParam insertMaterialConversion(MaterialConversionRequest materialConversionRequest, Integer platformCode) {
        Integer count = materialConversionRequest.getCount();
        if (platformCode == null || count == null || count <= 0) {
            return BaseResponseParam.fail("参数错误");
        }
        MaterialConversion materialConversion = converter.convert(materialConversionRequest, MaterialConversion.class);
        materialConversion.setPlatformCode(platformCode);
        for (int i = 0; i < count; i++) {
            materialConversion.setCode(UUIDGeneratorUtils.getUUIDUpper());
            materialConversionDAO.insertMaterialConversion(materialConversion);
        }
        return BaseResponseParam.success();
    }

    @Override
    public BaseResponseParam getMaterialConversionList(QueryStat queryStat) {
        if(queryStat.getPlatformCode() == null){
            return BaseResponseParam.fail("参数错误");
        }
        PageHelper.startPage(queryStat.getPageNum(),queryStat.getPageSize());
        List<MaterialConversionResponse> list = materialConversionDAO.getMaterialConversionList(queryStat);
        PageInfo<MaterialConversionResponse> pageInfo = new PageInfo<>(list);
        return BaseResponseParam.success(pageInfo);
    }


    @Override
    public Object getConversionByCode(QueryStat queryStat) {
        MaterialConversion materialConversion = materialConversionDAO.getConversionByCode(queryStat);
        if (materialConversion!=null){
            return BaseResponseParam.success(materialConversion);
        }
        return BaseResponseParam.fail("兑换码错误");
    }

    @Override
    public BaseResponseParam deleteMaterialConversion(QueryStat queryStat) {
        if(StringUtil.isEmptyStr(queryStat.getIds()) || queryStat.getPlatformCode() == null){
            BaseResponse.fail("参数错误");
        }
        int count = materialConversionDAO.deleteMaterialConversion(queryStat);
        if(count > 0){
            return BaseResponseParam.success();
        }
        return BaseResponseParam.fail();
    }

}
