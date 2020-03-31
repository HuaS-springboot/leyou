package com.vsj.service;

import com.vsj.model.VsjOrderOperation;

/**
 * @Classname OrderOperationService
 * @Description 订单操作记录接口
 * @Date 2019/8/8 10:29
 * @Created by wangzx
 */
public interface OrderOperationService {

    int insertOrderOperation(VsjOrderOperation vsjOrderOperation);

    Object selectOrderOperation(VsjOrderOperation  vsjOrderOperation);
}
