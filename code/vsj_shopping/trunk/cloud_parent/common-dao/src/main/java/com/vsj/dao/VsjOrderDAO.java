package com.vsj.dao;

import com.vsj.mapper.OrderMapper;
import com.vsj.model.VsjOrder;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Classname VsjOrderDAO
 * @Description TODO
 * @Date 2019/7/29 10:45
 * @Created by zy
 */
@Component
public class VsjOrderDAO {

    @Autowired
    private OrderMapper orderMapper;


    public  List<OrderResponse> getOrderList(BaseQueryStat queryStat) {
        return orderMapper.getOrderList(queryStat);
    }


    public OrderResponse getOrderDetailsList(BaseQueryStat queryStat) {
        return  orderMapper.getOrderDetailsList(queryStat);
    }

    public int updateOrderStatus(BaseQueryStat baseQueryStat) {
        return orderMapper.updateOrderStatus(baseQueryStat);
    }


    public List<OrderResponse> getOrderByIds(BaseQueryStat baseQueryStat) {
        return orderMapper.getOrderByIds(baseQueryStat);
    }

    public void updateLogisticsNo(VsjOrder vsjOrder) {
        orderMapper.updateLogisticsNo(vsjOrder);
    }

    public List<String> getProductIdsByMemberId(Integer memberId) {
        return orderMapper.getProductIdsByMemberId(memberId);
    }

    public Integer getOrderStatus(Map<String, Object> map) {
        return  orderMapper.getOrderStatus(map);
    }

    public void deleteOrder(Map<String, Object> map) {
        orderMapper.deleteOrder(map);
    }

    public Double getMemberConMoney(BaseQueryStat queryStat) {
        return orderMapper.getMemberConMoney(queryStat);
    }

    public int getProCount(BaseQueryStat queryStat) {
        return orderMapper.getProCount(queryStat);
    }

    public VsjOrder getOrderByOrderId(Integer orderId, Integer platformCode) {
        return orderMapper.getOrderByOrderId(orderId,platformCode);
    }

    public int updateOrder(OrderResponse orderResponse) {
        return orderMapper.updateOrder(orderResponse);
    }


    public VsjOrder getOrderByOrderNo(String orderNo, Integer platformCode) {
        return orderMapper.getOrderByOrderNo(orderNo,platformCode);
    }
}
