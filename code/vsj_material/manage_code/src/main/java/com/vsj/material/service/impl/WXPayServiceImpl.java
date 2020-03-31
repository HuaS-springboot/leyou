package com.vsj.material.service.impl;


import com.alibaba.fastjson.JSON;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.redis.constant.RedisKeyConstant;
import com.vsj.common.utils.*;
import com.vsj.material.constants.SysConfigConstants;
import com.vsj.material.constants.WXPayConstants;
import com.vsj.material.dao.VsjMaterialSysConfigDAO;
import com.vsj.material.model.MaterialMemberPackage;
import com.vsj.material.model.MaterialOrder;
import com.vsj.material.model.VsjMaterialMember;
import com.vsj.material.model.request.MaterialOrderRequest;
import com.vsj.material.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thlws.payment.WechatPayClient;
import org.thlws.payment.wechat.entity.request.UnifiedOrderRequest;
import org.thlws.payment.wechat.entity.response.UnifiedOrderResponse;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Classname WXPayServiceImpl
 * @Description 微信支付实现
 * @Date 2019/8/20 9:35
 * @Created by wangzx
 */
@Service
public class WXPayServiceImpl implements WXPayService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private VsjMaterialSysConfigDAO vsjMaterialSysConfigDAO;
    @Autowired
    private VsjMaterialOrderService vsjMaterialOrderService;
    @Autowired
    private MaterialPackageService materialPackageService;
    @Autowired
    private VsjMaterialMemberService vsjMaterialMemberService;


    @Override
    public BaseResponseParam unifiedOrder(MaterialOrderRequest materialOrderRequest, Integer platformCode, HttpServletRequest request) {
        if (platformCode == null) {
            return BaseResponseParam.fail("参数错误");
        }
        // 生成订单号
        String orderNo = String.valueOf(new OrderNoUtils(0, 1).nextId());

        UnifiedOrderRequest unifiedOrderRequest = getUnifiedOrderRequest(platformCode, request);
        // 获取api_key
        String apiKey = vsjMaterialSysConfigDAO.selectByConfigName(SysConfigConstants.WX_PAY_APISECRET, platformCode).getValue();
        // 回调地址
        unifiedOrderRequest.setNotifyUrl(WXPayConstants.WXPAY_NOTIFY_URL);
        // 支付金额-->转换为分
        unifiedOrderRequest.setTotalFee(materialOrderRequest.getPayAmount().multiply(new BigDecimal("100")).toString());
        // 类型=JSAPI
        unifiedOrderRequest.setTradeType(WXPayConstants.WXPAY_TRADE_TYPE);
        // 商户订单号
        unifiedOrderRequest.setOutTradeNo(orderNo);

        UnifiedOrderResponse unifiedOrderResponse = null;
        try {
            unifiedOrderResponse = WechatPayClient.unifiedOrder(unifiedOrderRequest, apiKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean success = unifiedOrderResponse.isSuccess();
        if (success) {
            // 如果成功，添加预订单
            MaterialOrder materialOrder = new MaterialOrder();
            materialOrder.setOrderNo(orderNo);
            materialOrder.setPackageId(materialOrderRequest.getPackageId());
            materialOrder.setPayAmount(materialOrderRequest.getPayAmount());
            materialOrder.setPlatformCode(platformCode);
            materialOrder.setRegId(materialOrderRequest.getRegId());
            materialOrder.setOrderPayType(0);
            int count = vsjMaterialOrderService.insertMaterialOrder(materialOrder);
            if (count > 0) {
                return BaseResponseParam.success();
            }
        }
        return BaseResponseParam.fail();
    }

    @Override
    public void wxpayNotify(HttpServletRequest request, HttpServletResponse response, Integer platformCode) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        System.out.println("接收到的报文：" + notityXml);
        //转map
        Map map = MapUtils.xmlToMap(notityXml);
        String resXml = "";
        logger.info("【小程序支付回调】 回调数据： \n" + map);
        String returnCode = (String) map.get("return_code");
        if ("SUCCESS".equalsIgnoreCase(returnCode)) {
            String returnmsg = (String) map.get("result_code");
            if ("SUCCESS".equals(returnmsg)) {
                // 获取商户订单号
                String orderNo = map.get("out_trade_no").toString();
                // 查询订单
                MaterialOrder materialOrder = vsjMaterialOrderService.getMaterialOrderByOrderNo(orderNo, platformCode);
                if (materialOrder != null) {
                    // 获取第三方支付订单号
                    String orderPayNo = map.get("transaction_id").toString();
                    // 更新订单状态及第三方流水号
                    materialOrder.setStatus(1);
                    materialOrder.setOrderPayNo(orderPayNo);
                    // 更新订单状态
                    vsjMaterialOrderService.updateMaterial(materialOrder);
                    // 更新会员等级
                    Integer packageId = materialOrder.getPackageId();
                    // 查询该套餐升级为的等级id
                    MaterialMemberPackage materialMemberPackage = materialPackageService.getMaterialPackageById(packageId, platformCode);
                    // 修改会员等级和会员过去时间
                    VsjMaterialMember vsjMaterialMember = new VsjMaterialMember();
                    vsjMaterialMember.setPlatformCode(platformCode);
                    vsjMaterialMember.setLevelId(materialMemberPackage.getLevelId());
                    vsjMaterialMember.setRegId(materialOrder.getRegId());
                    // 会员过期时间
                    vsjMaterialMember.setExpiredTime(DateUtil.addDay(materialMemberPackage.getDay()));
                    vsjMaterialMemberService.updateMaterialMember(vsjMaterialMember);
                    resXml = "SUCCESS";
                }
            } else {
                resXml = "FAIL";
                logger.info("支付失败:" + resXml);
            }
        } else {
            resXml = "FAIL";
            logger.info("【订单支付失败】");
        }
        logger.info("【小程序支付回调响应】 响应内容：\n" + resXml);
        response.getWriter().print(resXml);
    }

    private UnifiedOrderRequest getUnifiedOrderRequest(Integer platformCode, HttpServletRequest request) {
        // 获取app_id
        String appId = vsjMaterialSysConfigDAO.selectByConfigName(SysConfigConstants.WX_PAY_APPID, platformCode).getValue();
        // 获取mach_id
        String machId = vsjMaterialSysConfigDAO.selectByConfigName(SysConfigConstants.WX_PAY_MCHID, platformCode).getValue();
        UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
        unifiedOrderRequest.setAppId(appId);
        unifiedOrderRequest.setMchId(machId);
        // ip
        unifiedOrderRequest.setSpbillCreateIp(HttpUtils.getIp(request));
        return unifiedOrderRequest;
    }
}
