package com.vsj.dao;

import com.vsj.mapper.OrderOperationMapper;
import com.vsj.model.VsjOrderOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname OrderOperationDAO
 * @Description 订单操作DAO
 * @Date 2019/8/8 10:31
 * @Created by wangzx
 */
@Component
public class OrderOperationDAO {

    @Autowired
    private OrderOperationMapper orderOperationMapper;


    public int insertOrderOperation(VsjOrderOperation vsjOrderOperation) {
        return orderOperationMapper.insertOrderOperation(vsjOrderOperation);
    }

    public List<VsjOrderOperation> selectOrderOperation(VsjOrderOperation  vsjOrderOperation){
        return orderOperationMapper.selectOrderOperation(vsjOrderOperation);
    }

}
