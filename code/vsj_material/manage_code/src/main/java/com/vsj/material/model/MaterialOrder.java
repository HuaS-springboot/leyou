package com.vsj.material.model;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname MaterialOrder
 * @Description 订单model
 * @Date 2019/8/20 10:27
 * @Created by wangzx
 */
@Data
public class MaterialOrder implements Serializable {

    private Integer id;
    private String orderNo;
    private Integer regId;
    private Integer packageId;
    private BigDecimal payAmount;
    private Integer orderPayType;
    private String orderPayNo;
    private Integer status;
    private String createTime;
    private Integer platformCode;

}
