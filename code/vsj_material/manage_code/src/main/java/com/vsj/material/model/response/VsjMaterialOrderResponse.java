package com.vsj.material.model.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname VsjMaterialOrder
 * @Description 订单model
 * @Date 2019/8/15 9:24
 * @Created by wangzx
 */
@Data
public class VsjMaterialOrderResponse implements Serializable {

    private Integer id;
    private String orderNo;
    private Integer regId;
    private Integer packageId;
    private BigDecimal payAmount;
    private String createTime;
    private Integer platformCode;
    private String telPhone;
    private String nickName;
    private String levelName;
    private String expiredTime;
}
