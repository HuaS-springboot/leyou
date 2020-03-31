package com.vsj.material.service;

import com.vsj.material.model.MaterialOrder;
import com.vsj.material.model.request.QueryStat;

/**
 * @Classname VsjMaterialOrderService
 * @Description 订单相关接口
 * @Date 2019/8/15 9:14
 * @Created by wangzx
 */
public interface VsjMaterialOrderService {

    Object getMemberOrderList(QueryStat queryStat);

    int insertMaterialOrder(MaterialOrder materialOrder);

    MaterialOrder getMaterialOrderByOrderNo(String orderNo, Integer platformCode);

    int updateMaterial(MaterialOrder materialOrder);
}
