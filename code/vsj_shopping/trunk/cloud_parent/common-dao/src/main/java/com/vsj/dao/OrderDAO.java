package com.vsj.dao;

import com.vsj.mapper.CreateOrderMapper;
import com.vsj.model.VsjOrder;
import com.vsj.model.VsjOrderDetail;
import com.vsj.model.VsjProductSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname OrderDAO
 * @Description TODO
 * @Date 2019/8/2 10:38
 * @Created by zy
 */
@Component
public class OrderDAO {

    @Autowired
    private CreateOrderMapper createOrderMapper;
    /**
     * @Description
     * @Author zy
     * @Date   2019/8/2 10:40
     * @Param  [order]
     * @Return      java.lang.Integer
     * @Exception
     *
     */
    public VsjProductSpecs getOrderIsNullSell(Integer specsId) {
        return createOrderMapper.getOrderIsNullSell(specsId);
    }
    /**
    * @Description 查询未付款的订单总数
    * @Author zy
    * @Date   2019/8/2 11:54
    * @Param
    * @Return
    * @Exception
    *
    */
    public int getUnpaidOrderNumber(Integer regId) {
        return createOrderMapper.getUnpaidOrderNumber(regId);
    }
    /**
     * @Description 生成预订单
     * @Author zy
     * @Date   2019/8/2 14:08
     * @Param  [order]
     * @Return      int
     * @Exception
     *
     */
    public int insertOrder(VsjOrder order) {

        return createOrderMapper.insertOrder(order);
    }
    /**
     * @Description 添加订单详情信息
     * @Author zy
     * @Date   2019/8/2 16:09
     * @Param  [orderDetail]
     * @Return      int
     * @Exception
     *
     */
    public int insertOrderDetail(List<VsjOrderDetail> orderDetail) {
    return createOrderMapper.insertOrderDetail(orderDetail);
    }

    public VsjOrder getOrderByOrderNo(String orderNo) {
        return createOrderMapper.getOrderByOrderNo(orderNo);
    }
    /**
    * @Description  修改支付状态
    * @Author zy
    * @Date   2019/8/5 16:56
    * @Param
    * @Return
    * @Exception
    *
    */
    public int updateOrderStatus(String out_trade_no,String transaction_id) {
        return createOrderMapper.updateOrderStatus(out_trade_no,transaction_id);
    }
}
