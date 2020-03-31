package com.vsj.material.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.material.model.request.MaterialOrderRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname WXPayService
 * @Description 微信支付相关
 * @Date 2019/8/20 9:35
 * @Created by wangzx
 */
public interface WXPayService {

    BaseResponseParam unifiedOrder(MaterialOrderRequest materialOrderRequest, Integer platformCode, HttpServletRequest request);

    void wxpayNotify(HttpServletRequest request, HttpServletResponse response,Integer platformCode) throws Exception;
}
