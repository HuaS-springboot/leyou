package com.vsj.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname SysReview
 * @Description 审核model
 * @Date 2019/8/21 16:47
 * @Created by wangzx
 */
@Data
public class SysReview implements Serializable {

    private Integer id;
    private Integer regId;
    private Integer levelId;
    private Integer type;
    private BigDecimal withdrawAmount;
    private Integer status;
    private String createTime;
    private String fishTime;
    private Integer platformCode;
    private String paymentNo;
}
