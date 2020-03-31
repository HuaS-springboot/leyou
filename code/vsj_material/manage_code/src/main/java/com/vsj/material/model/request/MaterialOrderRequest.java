package com.vsj.material.model.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname MaterialOrderRequest
 * @Description 订单参数接受实体类
 * @Date 2019/8/20 9:31
 * @Created by wangzx
 */
@Data
public class MaterialOrderRequest implements Serializable {

    private String orderNo;
    private Integer regId;
    private Integer packageId;
    private BigDecimal payAmount;
}
