package com.vsj.material.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.material.model.request.MaterialConversionRequest;
import com.vsj.material.model.request.QueryStat;

/**
 * @Classname MaterialConversionService
 * @Description 兑换码相关接口
 * @Date 2019/8/16 10:25
 * @Created by wangzx
 */
public interface MaterialConversionService {

    BaseResponseParam insertMaterialConversion(MaterialConversionRequest materialConversionRequest,Integer platformCode);

    BaseResponseParam getMaterialConversionList(QueryStat queryStat);


    Object getConversionByCode(QueryStat queryStat);

    BaseResponseParam deleteMaterialConversion(QueryStat queryStat);

}
