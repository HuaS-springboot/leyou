package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.model.VsjOrder;
import com.vsj.model.response.OrderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Classname OrderService
 * @Description TODO
 * @Date 2019/7/24 16:21
 * @Created by zy
 */
public interface OrderService {

    Object getOrderList(QueryStat queryStat);

    Object getOrderDetailsList(QueryStat queryStat);

    Object updateOrderStatus(QueryStat queryStat);

    List<OrderResponse> getOrderByIds(QueryStat queryStat);

    void updateLogisticsNo(VsjOrder vsjOrder);

    void deleteOrder(String orderId);

    BaseResponseParam updateOrder(OrderResponse orderResponse);

    void wxRefundOrderNotify(HttpServletRequest request, HttpServletResponse response, Integer platformCode) throws Exception;

    void updateOutSignOrder(String orderNo);

    void updateSettleOrder(String orderNo);
}
