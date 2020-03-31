package com.vsj.service.Impl;

import com.alibaba.fastjson.JSON;
import com.ijpay.core.enums.SignType;
import com.ijpay.core.kit.WxPayKit;
import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.BaseController;
import com.vsj.common.config.WeChatApi;
import com.vsj.common.constants.OrderPayTypeConstants;
import com.vsj.common.constants.SysConfigConstants;
import com.vsj.common.handler.KafkaSender;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.Order;
import com.vsj.common.model.OrderList;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.redis.client.RedisClient;
import com.vsj.common.redis.constant.RedisKeyConstant;
import com.vsj.common.utils.MapUtils;
import com.vsj.dao.OrderDAO;
import com.vsj.dao.RegisterRecordDAO;
import com.vsj.dao.VsjRegisterDAO;
import com.vsj.dao.VsjSysConfigDAO;
import com.vsj.model.*;
import com.vsj.model.wechat.UnifiedOrderRequest;
import com.vsj.model.wechat.UnifiedOrderResponse;
import com.vsj.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Classname OrderServiceImpl
 * @Description TODO
 * @Date 2019/8/2 9:06
 * @Created by zy
 */
@Service
public class OrderServiceImpl extends BaseController implements OrderService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${kafka.topic.order}")
    private String topic;
    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private VsjSysConfigDAO vsjSysConfigDAO;

    @Autowired
    private AbstractObjectConverter<Order, VsjOrder> converter;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    WeChatApi weChatApi;

    @Autowired
    private KafkaSender kafkaSender;

    @Autowired
    private VsjRegisterDAO vsjRegisterDAO;

    @Autowired
    private RegisterRecordDAO registerRecordDAO;
    /**
     * @Description 用户下单
     * @Author zy
     * @Date   2019/8/2 9:10
     * @Param  [order]
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @Override
    public Object createOrderInfo(OrderList orderList) {
        List<String> list=new ArrayList<>();
        for (Order o : orderList.getOrders()){
            o.setPlatformCode(getPlatformCode());
            //查询未付款的订单数量
            int number = orderDAO.getUnpaidOrderNumber(o.getRegId());
            VsjSysConfig sysConfig = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.UNPAID_ORDER_NUM, o.getPlatformCode());
            if (sysConfig != null && sysConfig.getValue() != null) {
                if (number >= Integer.parseInt(sysConfig.getValue())) {
                    return BaseResponseParam.fail("未支付订单超出" + Integer.parseInt(sysConfig.getValue()) + "条，请删除未支付订单。");
                }
            }
            //查询该商品规格无库存是否可以销售
            for (VsjOrderDetail g : o.getOrderDetail()){
                VsjProductSpecs vsjProductSpecs = orderDAO.getOrderIsNullSell(g.getSpecsId());
                if (vsjProductSpecs.getProductStock() == 0) {
                    if (vsjProductSpecs.getIsnullSell() == 2) {
                        return BaseResponseParam.fail("商品库存不足");
                    }
                }
            }
            //TODO 获取优惠信息（暂时没有）
            //生成预订单
            VsjOrder vsjOrder = converter.convert(o, VsjOrder.class);
            //生成预订单
            int i = orderDAO.insertOrder(vsjOrder);
            if (i <= 0) {
                return BaseResponseParam.fail("下单失败");
            }
            o.getOrderDetail().stream().forEach(g -> {
                g.setOrderId(vsjOrder.getOrderId());
                g.setPlatformCode(vsjOrder.getPlatformCode());
            });
            //生成订单详情信息
            int j = orderDAO.insertOrderDetail(o.getOrderDetail());
            if (j <= 0){
                return BaseResponseParam.fail("下单失败");
            }
            String time = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.ORDER_CLOSE_TIME, o.getPlatformCode()).getValue();
            //把订单放进redis中监听过期时间
            redisClient.set(RedisKeyConstant.ORDER_LOSE+o.getOrderNo(),"",Long.parseLong(time), TimeUnit.HOURS);
            redisClient.set(RedisKeyConstant.VSJ_ORDER_CACHE+o.getOrderNo(), JSON.toJSON(vsjOrder));
            list.add(vsjOrder.getOrderNo());
        }
        if (list.size() == orderList.getOrders().size()) {

            return BaseResponseParam.success(list);
        }
        return  BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam payOrder(QueryStat queryStat) {

        if(OrderPayTypeConstants.WX_PAY.equals(queryStat.getPayType())){
            return this.wxPayOrder(queryStat);
        }else{
            return this.remSumPayOrder(queryStat);
        }
    }

    /**
     * @Description 余额支付
     * @Author zy
     * @Date   2019/8/26 11:30
     * @Param  [queryStat]
     * @Return      com.vsj.common.model.BaseResponseParam
     * @Exception
     *
     */
    private BaseResponseParam remSumPayOrder(QueryStat queryStat) {
        //判断订单是否存在有效
        VsjOrder o=orderDAO.getOrderByOrderNo(queryStat.getOrderNo());
        if (o==null){
            BaseResponseParam.fail("系统异常");
        }
        //扣除余额
        int count=vsjRegisterDAO.updateRegisterMoney(o);
        if (count>0){
            int i=orderDAO.updateOrderStatus(o.getOrderNo(),null);
            if (i>0){
                //查询支付完之后的余额
                VsjRegister register=vsjRegisterDAO.getUserById(o.getRegId(),o.getPlatformCode());
                //插入支出记录
                RegisterRecord record=new RegisterRecord();
                record.setCarryBalance(o.getOrderAmountTotal());
                record.setPlatformCode(o.getPlatformCode());
                record.setRegId(o.getRegId());
                record.setSource("购买商品："+o.getProductName());
                record.setType(1);
                record.setTotalBalance(register.getCarryBalance());
                int j=registerRecordDAO.insertRegisterRecord(record);
                if (j>0){
                //支付成功 业务处理
                //从缓存取出订单对象
                VsjOrder vsjOrder=(VsjOrder)redisClient.get(RedisKeyConstant.VSJ_ORDER_CACHE+o.getOrderNo());
                vsjOrder.setOrderStatus(1);
                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formatStr2 =formatter2.format(new Date());
                vsjOrder.setPayTime(formatStr2);
                vsjOrder.setUpdateTime(formatStr2);
                //修改好订单对象之后再放回缓存
                redisClient.set(RedisKeyConstant.VSJ_ORDER_CACHE+o.getOrderNo(), JSON.toJSON(vsjOrder));
                kafkaSender.asyncSendMessage(topic,o.getOrderNo());
                return BaseResponseParam.success();
                }
            }
        }
        return BaseResponseParam.fail();
    }

    /**
     * @Description 微信支付
     * @Author zy
     * @Date   2019/8/26 11:30
     * @Param  [queryStat]
     * @Return      com.vsj.common.model.BaseResponseParam
     * @Exception
     *
     */
    private BaseResponseParam wxPayOrder(QueryStat queryStat) {
        //判断订单是否存在有效
        VsjOrder o=orderDAO.getOrderByOrderNo(queryStat.getOrderNo());
        if (o==null){
            BaseResponseParam.fail("系统异常");
        }
        // 获取商户mchid
        String mchid = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.WX_PAY_MCHID, queryStat.getPlatformCode()).getValue();
        // 获取API KEY
        String apiKey = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.WX_PAY_APISECRET, queryStat.getPlatformCode()).getValue();
        // 获取mch_appid
        String mchAppId = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.WX_PAY_MCHAPPID, queryStat.getPlatformCode()).getValue();
        BigDecimal multiple = new BigDecimal(100);
        UnifiedOrderRequest request=new UnifiedOrderRequest();
        request.setSpbill_create_ip(queryStat.getIp());
        request.setOut_trade_no(queryStat.getOrderNo());
        request.setBody(o.getProductName());
        request.setTotal_fee((o.getOrderAmountTotal().multiply(multiple))+"");
        request.setMch_id(mchid);
        request.setKey(apiKey);
        request.setAppid(mchAppId);
        Map<String,String> map = weChatApi.unifiedorder(request);
        // 判断返回的结果
        String returnCode = map.get("return_code");
        String returnMsg = map.get("return_msg");
        if (!WxPayKit.codeIsOk(returnCode)) {
            return BaseResponseParam.fail(returnMsg);
        }
        String resultCode = map.get("result_code");
        if (!WxPayKit.codeIsOk(resultCode)) {
            BaseResponseParam.fail(returnMsg);
        }
        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
        String prepayId = map.get("prepay_id");
        // 二次签名，构建公众号唤起支付的参数,这里的签名方式要与上面统一下单请求签名方式保持一致
        Map<String, String> packageParams = WxPayKit.prepayIdCreateSign(prepayId,
                mchAppId,apiKey, SignType.HMACSHA256);
        // 将二次签名构建的数据返回给前端并唤起公众号支付
        String jsonStr = JSON.toJSONString(packageParams);
        return BaseResponseParam.success(jsonStr);
    }

    /**
     * @Description 微信异步回调
     * @Author zy
     * @Date   2019/8/5 10:02
     * @Param  [request, response]
     * @Return      void
     * @Exception
     *
     */
    @Override
    public void wxProPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine())!=null){
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        System.out.println("接收到的报文：" + notityXml);
        //转map
        Map map = MapUtils.xmlToMap(notityXml);
        String resXml = "";
        logger.info("【小程序支付回调】 回调数据： \n"+map);
        String returnCode = (String) map.get("return_code");
        if ("SUCCESS".equalsIgnoreCase(returnCode)) {
            String returnmsg = (String) map.get("result_code");
            if("SUCCESS".equals(returnmsg)){
                //TODO 处理业务
                int i=orderDAO.updateOrderStatus(map.get("out_trade_no").toString(),map.get("transaction_id").toString());
                if(i > 0){
                    //支付成功 业务处理
                    //从缓存取出订单对象
                    VsjOrder vsjOrder=(VsjOrder)redisClient.get(RedisKeyConstant.VSJ_ORDER_CACHE+map.get("out_trade_no").toString());
                    vsjOrder.setOrderStatus(1);
                    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formatStr2 =formatter2.format(new Date());
                    vsjOrder.setPayTime(formatStr2);
                    vsjOrder.setUpdateTime(formatStr2);
                    vsjOrder.setOutTradeNo(map.get("transaction_id").toString());
                    //修改好订单对象之后再放回缓存
                    redisClient.set(RedisKeyConstant.VSJ_ORDER_CACHE+map.get("out_trade_no").toString(), JSON.toJSON(vsjOrder));
                    kafkaSender.asyncSendMessage(topic,map.get("out_trade_no").toString());
                    resXml = "SUCCESS";
                }
            }else{
                resXml = "FAIL";
                logger.info("支付失败:"+resXml);
            }
        }else{
            resXml = "FAIL";
            logger.info("【订单支付失败】");
        }
        logger.info("【小程序支付回调响应】 响应内容：\n"+resXml);
        response.getWriter().print(resXml);
    }

}
