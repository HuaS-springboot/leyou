package com.vsj.material.constants;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname WXPayConstants
 * @Description 微信配置类
 * @Date 2019/8/20 16:08
 * @Created by wangzx
 */
@Data
public class WXPayConstants implements Serializable {

    /**
     * 微信支付类型 JSAPI
     */
    public static final String WXPAY_TRADE_TYPE = "JSAPI";

    /**
     * 微信支付回调地址(外网能访问)
     */
    public static final String WXPAY_NOTIFY_URL = "";
}
