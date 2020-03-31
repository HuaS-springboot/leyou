package com.vsj.material.controller;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.material.model.request.MaterialOrderRequest;
import com.vsj.material.service.WXPayService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname WXPayController
 * @Description 微信支付相关
 * @Date 2019/8/20 9:24
 * @Created by wangzx
 */
@RestController
@RequestMapping(value = "api/v1/wxpay", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin
public class WXPayController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WXPayService wxPayService;

    /**
     * @Description 购买会员
     * @Author  wangzx
     * @Date   2019/8/20 16:13
     * @Param  [materialOrderRequest]
     * regId:购买人id
     * packageId:套餐id
     * payAmount:付款金额
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "用户购买会员预支付", notes = "用户购买会员预支付", httpMethod = "POST")
    @PostMapping("/purchaseMember")
    public BaseResponseParam purchaseMember(@RequestBody MaterialOrderRequest materialOrderRequest){
        logger.info("用户购买会员预支付入参={}",materialOrderRequest);
        BaseResponseParam responseParam = wxPayService.unifiedOrder(materialOrderRequest,getPlatformCode(),request);
        logger.info("用户购买会员预支付入参={}",responseParam);
        return responseParam;
    }

    /**
     * @Description 微信支付回调
     * @Author  wangzx
     * @Date   2019/8/20 16:26
     * @Param  [request, response]
     * @Return      java.lang.Object
     * @Exception
     */
    @ApiOperation(value = "微信支付回调", notes = "微信支付回调", httpMethod = "POST")
    @PostMapping("/wxpayNotify")
    public void wxpayNotify(HttpServletRequest request,HttpServletResponse response) throws Exception{
        wxPayService.wxpayNotify(request,response,getPlatformCode());
    }
}
