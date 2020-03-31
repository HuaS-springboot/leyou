package com.vsj.service.impl;

import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.dao.OrderOperationDAO;
import com.vsj.model.VsjOrderOperation;
import com.vsj.service.OrderOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname OrderOperationServiceImpl
 * @Description 订单操作记录实现
 * @Date 2019/8/8 10:30
 * @Created by wangzx
 */
@Service
public class OrderOperationServiceImpl implements OrderOperationService {

    @Autowired
    private OrderOperationDAO orderOperationDAO;

    @Override
    public int insertOrderOperation(VsjOrderOperation vsjOrderOperation) {
        return orderOperationDAO.insertOrderOperation(vsjOrderOperation);
    }

    @Override
    public Object selectOrderOperation(VsjOrderOperation  vsjOrderOperation) {
        List<VsjOrderOperation> orderOperation = orderOperationDAO.selectOrderOperation(vsjOrderOperation);
        if(orderOperation.size()<0 && orderOperation==null){
            return BaseResponseParam.fail();
        }
        return BaseResponseParam.success(orderOperation);
    }


}
