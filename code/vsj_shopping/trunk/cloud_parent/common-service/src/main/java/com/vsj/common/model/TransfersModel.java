package com.vsj.common.model;

import com.ijpay.core.model.BaseModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @Classname TransfersModel
 * @Description 微信企业付款到零钱MODEL
 * @Date 2019/8/21 17:48
 * @Created by wangzx
 * https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2
 */
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TransfersModel extends BaseModel {
    /**
     * 商户账号appid
     */
    private String mch_appid;

    /**
     * 商户号
     */
    private String mchid;

    /**
     * 设备号
     */
    private String device_info;

    /**
     * 随机字符串
     */
    private String nonce_str;

    /**
     *签名
     */
    private String sign;

    /**
     * 商户订单号
     */
    private String partner_trade_no;

    /**
     * 用户openid
     */
    private String openid;

    /**
     * 校验用户姓名选项
     */
    private String check_name;

    /**
     * 收款用户姓名
     */
    private String re_user_name;

    /**
     * 金额 单位分
     */
    private String amount;

    /**
     * 企业付款备注
     */
    private String desc;

    /**
     * Ip地址
     */
    private String spbill_create_ip;
}
