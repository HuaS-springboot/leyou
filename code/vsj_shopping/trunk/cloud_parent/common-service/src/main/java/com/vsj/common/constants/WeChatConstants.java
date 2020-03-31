package com.vsj.common.constants;

/**
 * @Classname WeChatConstants
 * @Description 微信配置常量
 * @Date 2019/8/22 9:36
 * @Created by wangzx
 */
public interface WeChatConstants {

    /**
     * 微信支付证书下载地址
     */
    public static final String WECHAT_CERT_DOWNLOAD_PATH = "D:/download/cert/";

    /**
     * 微信退款回调地址
     */
    public static final String ORDER_REFUND_NOTIFYURL = "localhost:8751/api/v1/order/wxRefundOrderNotify";
}
