package com.vsj.common.config;

import com.alibaba.fastjson.JSON;
import com.ijpay.core.enums.SignType;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.model.UnifiedOrderModel;
import com.thoughtworks.xstream.XStream;
import com.vsj.common.redis.client.RedisClient;
import com.vsj.common.utils.*;
import com.vsj.model.wechat.Jscode2SessionResponse;
import com.vsj.model.wechat.UnifiedOrderRequest;
import com.vsj.model.wechat.UnifiedOrderResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thlws.payment.WechatPayClient;
import org.thlws.payment.wechat.entity.request.WechatRefundRequest;
import org.thlws.payment.wechat.entity.response.WechatRefundResponse;

import java.util.Map;

@Component
public class WeChatApi {

    //获取openID的json的接口
    private static final String WEB_ACCESS_TO_KEN_HTTPS = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
    //支付成功后的服务器回调url
    private static final String NOTIFY_URL = "https://www.gkstop.com:8443";
    //交易类型，小程序支付的固定值为JSAPI
    private static final String TRADETYPE = "JSAPI";
    //提现的接口
    private static final String WITHDRAWALS_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    //获取菊花码生成access_token生成接口
    private static final String WX_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    //获取菊花码本人用的是B,传值(数量无限制）
    private static final String WX_CODE = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s";
    //微信统一下单接口地址
    private static final String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public static final String TEMPLATE_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=%s";

    @Autowired
    private RedisClient redisAssistant;

    /**
     * 获取用户openid
     */
    public Jscode2SessionResponse getWebAccess(UnifiedOrderRequest obj) {
        String url = String.format(WeChatApi.WEB_ACCESS_TO_KEN_HTTPS,obj.getAppid(), obj.getKey(), obj.getCode());
        String response = HttpClient.getInstance()
                .get(url, null);
        try {
            return JSONUtils.toObject(response, Jscode2SessionResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 统一下单
     */
    public Map<String,String> unifiedorder(UnifiedOrderRequest obj) {
        // 统一下单构建请求参数
        Map<String, String> params = UnifiedOrderModel
                .builder()
                .appid(obj.getAppid())
                .mch_id(obj.getMch_id())
                .nonce_str(WxPayKit.generateStr())
                .body(obj.getBody())
                .out_trade_no(WxPayKit.generateStr())
                .total_fee(obj.getTotal_fee()+"")
                .spbill_create_ip(obj.getSpbill_create_ip())
                .notify_url(NOTIFY_URL)
                .trade_type(TRADETYPE)
                .build()
                // 同时支持 SignType.MD5、SignType.HMACSHA256
                .creatSign(obj.getKey(), SignType.HMACSHA256);
        // 发送请求
        String xmlResult = WxPayApi.pushOrder(false,params);
        // 将请求返回的 xml 数据转为 Map，方便后面逻辑获取数据
        Map<String, String> resultMap = WxPayKit.xmlToMap(xmlResult);
        return resultMap;
    }


    private String nonceStr() {
        return UUIDGeneratorUtils.getUUIDUpper();
    }


    /**
     * 微信退款
     *
     * @param outTradeNo:微信支付订单号
     * @param payFree:原订单金额
     * @param refundAmount:实际退款订单金额
     * @param p12FilePath:p12证书地址
     * @return
     */
    public boolean refundOrder(String outTradeNo, Integer payFree, Integer refundAmount, String p12FilePath) {
        try {
            WechatRefundRequest wechatRefundRequest = new WechatRefundRequest();
          /*  wechatRefundRequest.setAppId(APP_ID);
            wechatRefundRequest.setMchId(MCH_ID);*/
            wechatRefundRequest.setRefundDesc("商家退款");
            // 退款单号
            wechatRefundRequest.setOutRefundNo(UUIDGeneratorUtils.getUUIDUpper());
            // 商户订单号
            wechatRefundRequest.setOutTradeNo(outTradeNo);
            // 原订单金额(单位分)
            wechatRefundRequest.setTotalFee(String.valueOf(payFree));
            // 退款金额(单位分)
            wechatRefundRequest.setRefundAccount(String.valueOf(refundAmount));
            WechatRefundResponse refundResponse = WechatPayClient.refund(wechatRefundRequest, "", p12FilePath);
            // AlipayClient.
            if (refundResponse != null) {
                return refundResponse.isSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
