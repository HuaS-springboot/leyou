package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.OrderList;
import com.vsj.common.model.request.QueryStat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname OrderServiceImpl
 * @Description TODO
 * @Date 2019/8/2 9:02
 * @Created by zy
 */
public interface OrderService {
    /**
    * @Description 用户下单
    * @Author zy
    * @Date   2019/8/2 9:10
    * @Param
    * @Return
    * @Exception
    *
    */
    Object createOrderInfo(OrderList order);
    /**
    * @Description  微信支付订单
    * @Author zy
    * @Date   2019/8/2 18:10
    * @Param
    * @Return
    * @Exception
    *
    */
    BaseResponseParam payOrder(QueryStat queryStat);
    /**
    * @Description  微信异步回调
    * @Author zy
    * @Date   2019/8/5 10:01
    * @Param
    * @Return
    * @Exception
    *
    */
    void wxProPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
