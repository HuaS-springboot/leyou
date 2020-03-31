package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.ProAttrList;
import com.vsj.common.model.request.QueryStat;

/**
 * @Classname ProductAttrKeyService
 * @Description TODO
 * @Date 2019/7/25 17:37
 * @Created by wangzx
 */
public interface ProductAttrKeyService {

    BaseResponseParam insertProductAttrKey(ProAttrList proAttrList);

    BaseResponseParam getAttrList(QueryStat queryStat);
}
