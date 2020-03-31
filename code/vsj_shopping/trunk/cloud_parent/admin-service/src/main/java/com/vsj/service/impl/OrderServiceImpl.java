package com.vsj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ijpay.core.enums.SignType;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.model.RefundModel;
import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.BaseController;
import com.vsj.common.config.WeChatApi;
import com.vsj.common.constants.*;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.utils.MapUtils;
import com.vsj.dao.*;
import com.vsj.model.*;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.OrderResponse;
import com.vsj.service.*;
import io.netty.util.internal.UnstableApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname OrderServiceImpl
 * @Description 订单管理相关接口实现类
 * @Date 2019/7/24 16:22
 * @Created by zy
 */
@Service
public class OrderServiceImpl extends BaseController implements OrderService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private VsjOrderDAO orderDAO;
    @Autowired
    private OrderOperationService orderOperationService;
    @Autowired
    private WeChatApi weChatApi;
    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> queryStatConvert;
    @Autowired
    private VsjSysConfigDAO vsjSysConfigDAO;
    @Autowired
    private RegisterAddressDAO registerAddress;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private VsjMemberDAO vsjMemberDAO;
    @Autowired
    private VsjOrderReceiptsRecordDAO vsjOrderReceiptsRecordDAO;
    @Autowired
    private RegisterService registerService;

    @Override
    public Object getOrderList(QueryStat queryStat) {
        PageHelper.startPage(queryStat.getPageNum(), queryStat.getPageSize());
        queryStat.setPlatformCode(getPlatformCode());
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        List<OrderResponse> list = orderDAO.getOrderList(baseQueryStat);
        PageInfo<OrderResponse> pageInfo = new PageInfo<>(list);
        return BaseResponseParam.success(pageInfo);
    }

    @Override
    public Object getOrderDetailsList(QueryStat queryStat) {
        queryStat.setPlatformCode(getPlatformCode());
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        OrderResponse orderDetails = orderDAO.getOrderDetailsList(baseQueryStat);
        return BaseResponseParam.success(orderDetails);
    }

    @Override
    public Object updateOrderStatus(QueryStat queryStat) {
        if(queryStat.getOrderId() == null){
            return BaseResponse.fail("参数错误");
        }

        Integer status = queryStat.getStatus();
        //确认退款通过/不通过之前的状态
        Integer beforeStatus = queryStat.getBeforeStatus();
        // 退款通过/不通过
        Integer type = queryStat.getType();
        // 订单id
        Integer orderId = queryStat.getOrderId();
        // 后台操作人id
        Integer sysUserId = queryStat.getSysUserId();
        // 当前的登录会员id
        Integer memberId = queryStat.getMemberId();
        // 备注
        String remark = queryStat.getRemark();
        queryStat.setPlatformCode(getPlatformCode());
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        if (queryStat.getPrice() !=null){
            int i=orderDAO.updateOrderStatus(baseQueryStat);
            if (i>0){
                return BaseResponse.success();
            }
        }
        if (status != null && status == 4) {
            //TODO 去redis中删除key,处理分润业务。

        }
        if (status == OrderStatusConstants.ORDER_STATUS_PENDING_DELIVERED || status==OrderStatusConstants.ORDER_STATUS_PENDING_RECEIPT  ||
                status== OrderStatusConstants.ORDER_STATUS_RECEIVED || status==OrderStatusConstants.ORDER_STATUS_CANCEL_DELIVERY){
           int i=orderDAO.updateOrderStatus(baseQueryStat);
           if (i>0){
               return BaseResponse.success();
           }
        }
        // 退款审核业务
        if (beforeStatus != null && (beforeStatus == OrderStatusConstants.ORDER_STATUS_NO_SHIPPING_REFUND ||
                beforeStatus == OrderStatusConstants.ORDER_STATUS_SHIPPING_REFUND)) {
            if (type == null) {
                return BaseResponse.fail("参数错误");
            }
            if (type == 1) {
                // 退款审核通过
                VsjOrder vsjOrder = orderDAO.getOrderByOrderId(orderId, getPlatformCode());
                if (vsjOrder == null) {
                    return BaseResponse.fail("退款失败,未找到该订单.");
                }
                // 退款审核通过，付款金额原路返回
                if(OrderPayTypeConstants.WX_PAY.equals(vsjOrder.getPayChannel())){
                    //支付方式微信
                    Map<String,String> resultMap = wxRefundOrder(vsjOrder);
                    String returnCode = resultMap.get("return_code");
                    String returnMsg = resultMap.get("return_msg");
                    if (!WxPayKit.codeIsOk(returnCode)) {
                        return BaseResponseParam.fail(returnMsg);
                    }
                    String resultCode = resultMap.get("result_code");
                    if (!WxPayKit.codeIsOk(resultCode)) {
                        return BaseResponseParam.fail(returnMsg);
                    }
                    return BaseResponseParam.success();
                }else if(OrderPayTypeConstants.ZFB_PAY.equals(vsjOrder.getPayChannel())){
                    // 支付宝支付
                    return BaseResponseParam.success();
                }else if(OrderPayTypeConstants.SYSTEMC_PAY.equals(vsjOrder.getPayChannel())){
                    // 余额付款
                    systemPayRefund(vsjOrder);
                    return BaseResponseParam.success();
                }
                return BaseResponseParam.fail();
            } else {
                // 退款审核不通过
                int c = refundNoPass(baseQueryStat);
                if (c > 0) {
                    return BaseResponse.success();
                }
                return BaseResponse.fail();
            }
        }
        return BaseResponseParam.fail();
    }

    /**
     * 余额支付——>退款逻辑
     * @param vsjOrder
     */
    private void systemPayRefund(VsjOrder vsjOrder) {
        refundPass(vsjOrder.getOrderNo(),vsjOrder.getPlatformCode(),"");
        // 4、增加会员账户余额
        VsjRegister vsjRegister = new VsjRegister();
        vsjRegister.setId(vsjOrder.getRegId());
        vsjRegister.setCarryBalance(vsjOrder.getOrderAmountTotal());
        vsjRegister.setPlatformCode(vsjOrder.getPlatformCode());
        registerService.updateRegisterById(vsjRegister);
    }

    private void refundPass(String orderNo,Integer platformCode,String refundId) {
        // 查询订单
        VsjOrder vsjOrder = orderDAO.getOrderByOrderNo(orderNo, getPlatformCode());
        // 1、修改订单状态为已退款
        BaseQueryStat baseQueryStat = new BaseQueryStat();
        baseQueryStat.setOrderId(vsjOrder.getOrderId());
        baseQueryStat.setStatus(OrderStatusConstants.ORDER_STATUS_REFUNDED);
        baseQueryStat.setPlatformCode(vsjOrder.getPlatformCode());
        orderDAO.updateOrderStatus(baseQueryStat);
        // 2、增加订单操作记录为已退款
        VsjOrderOperation vsjOrderOperation = new VsjOrderOperation();
        vsjOrderOperation.setOperateType(OrderStatusConstants.ORDER_STATUS_REFUNDED);
        vsjOrderOperation.setOrderId(vsjOrder.getOrderId());
        vsjOrderOperation.setRegId(vsjOrder.getRegId());
        vsjOrderOperation.setPlatformCode(getPlatformCode());
        orderOperationService.insertOrderOperation(vsjOrderOperation);
        // 3、退款增加会员订单收入明细
        VsjOrderReceiptsRecord vsjOrderReceiptsRecord = new VsjOrderReceiptsRecord();
        vsjOrderReceiptsRecord.setOrderId(vsjOrder.getOrderId());
        vsjOrderReceiptsRecord.setMemberId(vsjOrder.getMemberId());
        vsjOrderReceiptsRecord.setOperationMoney(vsjOrder.getOrderAmountTotal());
        vsjOrderReceiptsRecord.setType(PaymentTypeConstants.INCOME);
        vsjOrderReceiptsRecord.setIncomeSource(IncomeSourceConstants.ORDER_REFUND);
        vsjOrderReceiptsRecord.setPlatformCode(platformCode);
        vsjOrderReceiptsRecord.setIsSettle(RecordIsSettleConstants.IS_SETTLE);
        VsjMember member = vsjMemberDAO.selectByPrimaryKey(vsjOrder.getMemberId());
        vsjOrderReceiptsRecord.setLevelId(member.getLevelId());
        vsjOrderReceiptsRecordDAO.insert(vsjOrderReceiptsRecord);
    }

    /**
     * 微信退款
     * @param vsjOrder
     * @return
     */
    private Map<String, String> wxRefundOrder(VsjOrder vsjOrder) {
        // 调用微信退款
        // 订单实付金额(转换为分)
        String totalFree = vsjOrder.getOrderAmountTotal().multiply(new BigDecimal(100)).toString();
        // 下载微信退款使用的证书 获取地址
        String certFileName = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.WX_PAY_CERT_FILE,vsjOrder.getPlatformCode()).getValue();
        String certPath = uploadService.downloadFile(WeChatConstants.WECHAT_CERT_DOWNLOAD_PATH, certFileName, vsjOrder.getPlatformCode());
        // 获取appId
        String appId = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.WX_PAY_APPID, vsjOrder.getPlatformCode()).getValue();
        // 获取商户mchid
        String mchid = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.WX_PAY_MCHID, vsjOrder.getPlatformCode()).getValue();
        // 获取API KEY
        String apiKey = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.WX_PAY_APISECRET, vsjOrder.getPlatformCode()).getValue();
        Map<String, String> paramMap = RefundModel.builder()
                .appid(appId)
                .mch_id(mchid)
                .nonce_str(WxPayKit.generateStr())
                .transaction_id(vsjOrder.getOutTradeNo())
                .out_refund_no(WxPayKit.generateStr())
                .total_fee(totalFree)
                .refund_account(totalFree)
                .notify_url(WeChatConstants.ORDER_REFUND_NOTIFYURL)
                .refund_desc("商家退款")
                .build()
                .creatSign(apiKey, SignType.HMACSHA256);
        String xmlResult = WxPayApi.orderRefund(false, paramMap, certPath, mchid);
        return WxPayKit.xmlToMap(xmlResult);
    }

    private int refundNoPass(BaseQueryStat baseQueryStat) {
        // 1、修改订单状态
        // 2、添加操作记录
        VsjOrderOperation vsjOrderOperation = new VsjOrderOperation();
        if (baseQueryStat.getStatus() == OrderStatusConstants.ORDER_STATUS_NO_SHIPPING_REFUND) {
            // 未发货退款审核不通过,更改状态为待收货
            baseQueryStat.setStatus(OrderStatusConstants.ORDER_STATUS_PENDING_DELIVERED);
            // 未发货退款审核未通过
            vsjOrderOperation.setOperateType(OrderStatusConstants.ORDER_OPER_STATUS_NO_SHIPPING_REFUND_NO_PASS);
        } else if (baseQueryStat.getStatus() == OrderStatusConstants.ORDER_STATUS_SHIPPING_REFUND) {
            // 已发货退款审核不通过,更改状态为待收货
            baseQueryStat.setStatus(OrderStatusConstants.ORDER_STATUS_PENDING_RECEIPT);
            // 已发货退款审核未通过
            vsjOrderOperation.setOperateType(OrderStatusConstants.ORDER_OPER_STATUS_SHIPPING_REFUND_NO_PASS);
        }
        int oc = orderDAO.updateOrderStatus(baseQueryStat);
        if (oc > 0) {
            vsjOrderOperation.setOrderId(baseQueryStat.getOrderId());
            vsjOrderOperation.setRegId(baseQueryStat.getRegId());
            vsjOrderOperation.setSysUserId(baseQueryStat.getSysUserId());
            vsjOrderOperation.setRemark(baseQueryStat.getRemark());
            vsjOrderOperation.setPlatformCode(baseQueryStat.getPlatformCode());
            int opc = orderOperationService.insertOrderOperation(vsjOrderOperation);
            return opc;
        }
        return 0;
    }

    @Override
    public List<OrderResponse> getOrderByIds(QueryStat queryStat) {
        queryStat.setPlatformCode(getPlatformCode());
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        return orderDAO.getOrderByIds(baseQueryStat);
    }

    @Override
    public void updateLogisticsNo(VsjOrder vsjOrder) {
        orderDAO.updateLogisticsNo(vsjOrder);
    }

    @Override
    public void deleteOrder(String orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("platformCode", getPlatformCode());
        Integer status = orderDAO.getOrderStatus(map);
        if (status == 0) {
            orderDAO.deleteOrder(map);
        }
    }

    @Override
    public BaseResponseParam updateOrder(OrderResponse orderResponse) {
        if(orderResponse.getOrderId()==null){
            return BaseResponseParam.fail("参数错误");
        }
        int i=orderDAO.updateOrder(orderResponse);
        if (i>0){
            return BaseResponseParam.success();
        }
        return BaseResponseParam.fail();
    }

    /**
     * 微信退款回调
     * @param request
     * @param response
     * @param platformCode
     * @throws Exception
     */
    @Override
    public void wxRefundOrderNotify(HttpServletRequest request, HttpServletResponse response, Integer platformCode) throws  Exception{
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
                // 获取微信退款单号
                String refundId = map.get("refund_id").toString();
                // 处理退款业务
                refundPass(orderNo,platformCode,refundId);
                resXml = "SUCCESS";
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

    /**
     * @Description 自动签单
     * @Author zy
     * @Date   2019/8/27 15:06
     * @Param  [orderNo] 订单号
     * @Return      void
     * @Exception
     *
     */
    @Override
    public void updateOutSignOrder(String orderNo) {

    }
    /**
     * @Description 自动结算
     * @Author zy
     * @Date   2019/8/27 15:06
     * @Param  [orderNo]
     * @Return      void
     * @Exception
     *
     */
    @Override
    public void updateSettleOrder(String orderNo) {

    }
}
