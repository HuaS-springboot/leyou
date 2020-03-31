package com.vsj.common.model;

import com.vsj.model.VsjOrderDetail;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @ClassName: Order
 * @Description: 订单业务实体对象
 * @author: mario 
 * @date: 2019年8月1日 下午5:10:36
 * @copyright: 青岛微视角文化传媒有限公司
 */
@Data
public class Order implements Serializable{

	private static final long serialVersionUID = 6578721285651930484L;
	/**
	 * 订单Id
	 */
    private Integer	orderId;
    /**
     * 订单号
     */
    private String	orderNo;
    /**
     * 注册用户Id
     */
	private Integer regId;
    /**
     * 上级邀请码
     */
    private Integer referrerId;
	/**
	 * 会员Id
	 */
	private Integer	memberId;
	/**
	 * 订单创建时间
	 */
    private String	createTime;
    /**
     * 付款时间
     */
    private String	payTime;
    /**
     * 平台码
     */
    private Integer	platformCode;
    /**
     * 商品应收
     */
    private BigDecimal productAmountTotal;
    /**
     * 运费金额
     */
    private BigDecimal logisticsFee;
    /**
     * 订单实收总额
     */
    private BigDecimal orderAmountTotal;
    /**
     * 商品Id
     */
    private Integer productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品总数量
     */
    private Integer productCount;
    /**
     * 是否为分红订单 0为普通订单,1为分红订单
     */
    private Byte dividendOrder;
    /**
     * 是否开启独立提成0=关  1=开',
     */
    private Byte isCommission;
    /**
     * 订单类型 0为普通订单,1活动订单
     */
    private Integer orderType;
    /**
     * 收货地址ID
     */
    private Integer addressId;
    /**
     * 订单明细集合
     */
    private List<VsjOrderDetail> orderDetail;

}
