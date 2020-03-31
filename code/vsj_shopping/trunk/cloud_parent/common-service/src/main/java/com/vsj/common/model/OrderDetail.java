package com.vsj.common.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname OrderDetail
 * @Description 订单明细业务实体类
 * @Date 2019/8/1 19:35
 * @Created by zy
 */
@Data
public class OrderDetail implements Serializable {

    /**
     *规格ID
     */
    private Integer specsId;
    /**
     * 商品价格
     */
    private BigDecimal productPrice;
    /**
     *商品规格参数
     */
    private String paramsJson;
    /**
     *购买数量
     */
    private Integer number;
    /**
     * 小计金额
     */
    private BigDecimal subtotal;
    /**
     * 订单ID
     */
    private Integer orderId;
    /**
     * 平台码
     */
    private Integer platformCode;
}
