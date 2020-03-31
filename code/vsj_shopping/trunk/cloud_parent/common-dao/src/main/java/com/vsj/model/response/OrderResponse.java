package com.vsj.model.response;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.vsj.model.VsjOrderDetail;
import com.vsj.model.VsjOrderReceiptsRecord;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname OrderResponse
 * @Description TODO
 * @Date 2019/7/24 16:04
 * @Created by zy
 */
@Data
public class OrderResponse implements Serializable {

    private BigInteger orderId;
    @Excel(name = "订单号", orderNum = "0",width = 30)
    private String     orderNo;
    @Excel(name = "商品名", orderNum = "1",width = 30)
    private String     productName;
    @Excel(name = "用户名", orderNum = "2",width = 30)
    private String     nickName;
    @Excel(name = "支付渠道",replace = { "微信_WX", "支付宝_ZFB" },orderNum="3",width = 30)
    private String    payChannel;
    @Excel(name = "应收",orderNum = "4",width = 30)
    private BigDecimal productAmountTotal;
    @Excel(name = "实收",orderNum = "5",width = 30)
    private BigDecimal orderAmountTotal;
    @Excel(name = "下单时间",orderNum = "6",width = 30)
    private String     createTime;
    @Excel(name = "支付时间",orderNum = "7",width = 30)
    private String     payTime;
    @Excel(name = "订单状态",replace = { "待付款_0", "待发货_1","待收货_2","已签收_3","已结算_4","未发货退款_5","已发货退款_6","已退款_7","已关闭_8" },orderNum = "8",width = 30)
    private Integer    orderStatus;
    /**
     * 会员Id
     */
    private Integer memberId;
    /**
     * 收货人
     */
    private String realName;
    /**
     * 收货电话
     */
    private String telPhone;
    /**
     * 收货地址
     */
    private String site;
    /**
     * 用户备注
     */
    private String userRemark;
    /**
     *  商户备注
     */
    private String shopRemark;
    /**
     * 奖励详情集合
     */
    private List<VsjOrderReceiptsRecord> orderRecord = new ArrayList<>();
    /**
     * 订单商品集合
     */
    private List<VsjOrderDetail>     orderDetail = new ArrayList<>();
    /**
     * 平台码
     */
    private Integer platformCode;
    /**
     * 商品ID
     */
    private Integer productId;
}
