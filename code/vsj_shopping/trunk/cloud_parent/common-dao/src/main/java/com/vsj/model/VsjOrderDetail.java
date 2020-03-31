package com.vsj.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @Classname VsjOrderDetail
 * @Description TODO
 * @Date 2019/7/25 10:14
 * @Created by zy
 */
@Data
public class VsjOrderDetail implements Serializable {

    private static final long serialVersionUID = -4729763901505243112L;

    private Integer detailId;
    private Integer orderId;
    private Integer    specsId;
    private BigDecimal productPrice;
    private String     paramsJson;
    private Integer    number;
    private BigDecimal subtotal;
    private Integer    isProductExists;
    private String     remark;
    private Integer     platformCode;
}
