package com.vsj.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class VsjShoppingCart implements Serializable {
    private Integer cartId;

    private Integer id;

    private Integer regId;

    private Integer productId;

    private Integer shopId;

    private Integer number;

    private Byte isProductExists;

    private Integer productSpecsId;

    private String createdTime;

    private Integer platformCode;

    private String productName;

    private String productImage;

    private BigDecimal price;

    private String attrJson;
}
