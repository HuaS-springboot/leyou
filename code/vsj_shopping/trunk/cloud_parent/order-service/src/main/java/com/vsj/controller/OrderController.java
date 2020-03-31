package com.vsj.controller;

import com.vsj.common.BaseController;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.Order;
import com.vsj.common.model.OrderList;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.utils.HttpUtils;
import com.vsj.common.utils.StringUtil;
import com.vsj.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname OrderController
 * @Description 订单相关接口
 * @Date 2019/8/1 15:01
 * @Created by zy
 */
@RestController
@RequestMapping(value="/api/order/",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OrderController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderService orderService;

    @RequestMapping("createOrderInfo")
    public Object createOrderInfo(@RequestBody OrderList order){

        logger.debug("下单接口createOrderInfo入参"+order);
        return orderService.createOrderInfo(order);
    }
    /**
     * @Description 微信支付接口
     * @Author zy
     * @Date   2019/8/2 18:05
     * @Param  [request, orderNo 订单号]
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @RequestMapping("wxPayOrder")
    public BaseResponseParam payOrder(HttpServletRequest request, @RequestBody QueryStat queryStat){
        if(StringUtil.isEmptyStr(queryStat.getOrderNo())){
            return BaseResponseParam.fail("参数错误");
        }
        if (null == getPlatformCode()) {
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        logger.debug("code"+queryStat);
        queryStat.setPlatformCode(getPlatformCode());
        String ip= HttpUtils.getIp(request);
        queryStat.setIp(ip);
        long startTime = System.currentTimeMillis();
        BaseResponseParam responseParam=orderService.payOrder(queryStat);
        logger.info("信息请求完成,返回参数={},耗时={}", responseParam, (System.currentTimeMillis() - startTime));
        return responseParam;
    }
    /**
    * @Description 微信回调
    * @Author zy
    * @Date   2019/8/5 14:41
    * @Param
    * @Return
    * @Exception
    *
    */
    @RequestMapping("wxProPayNotify")
    public void wxProPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        orderService.wxProPayNotify(request,response);
    }
}
