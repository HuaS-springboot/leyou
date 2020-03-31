package com.vsj.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * @Classname VsjOrder
 * @Description TODO
 * @Date 2019/7/24 15:08
 * @Created by zy
 */
@Data
public class VsjOrder implements Serializable {

    private static final long serialVersionUID = 7419052099573400185L;

    private Integer orderId;
    @Excel(name = "订单号", orderNum = "0",width = 30)
    private String     orderNo;
    private Integer    shopId;
    private Integer    orderStatus;
    private Integer    productCount;
    private BigDecimal productAmountTotal;
    private BigDecimal orderAmountTotal;
    private BigDecimal logisticsFee;
    private Integer    addressId;
    @Excel(name = "快递单号", orderNum = "1",width = 30)
    private String     logisticsNo;
    private String    payChannel;
    private String     outTradeNo;
    private String       createTime;
    private String       payTime;
    private String       deliverTime;
    private String     userRemark;
    private Integer     platformCode;
    private Integer orderLogisticsId;
	private Integer regId;//当前人id
	private Integer memberId;
    private String    logisticsType;
    private String  updateTime;
    private Integer referrerId;//上级邀请人id
    private Byte dividendOrder;
    private Integer orderType;
    private String productName;
    private Integer productId;
}
