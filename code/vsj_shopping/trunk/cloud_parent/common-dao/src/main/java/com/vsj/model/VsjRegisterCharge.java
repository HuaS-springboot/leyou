package com.vsj.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname VsjRegisterCharge
 * @Description TODO
 * @Date 2019/8/26 16:59
 * @Created by zy
 */
@Data
public class VsjRegisterCharge implements Serializable {

    private Integer id;
    private BigDecimal chargeMoney;
    private String chargeNo;
    private Integer status;
    private String payChannel;
    private String  outTradeNo;
    private String  payTime;
    private Integer regId;
    private String  createTime;
    private Integer platformCode;
}
