package com.vsj.common.model.request;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname VsjProductRequest
 * @Description TODO
 * @Date 2019/8/2 10:04
 * @Created by wangzx
 */
@Data
public class VsjProductRequest implements Serializable {

    private static final long serialVersionUID = 6372396284446729680L;

    private Integer productId;

    private String productCode;

    private String productName;

    private String productImage;

    private Integer brandId;

    private Integer oneCategoryId;

    private Integer twoCategoryId;

    private Integer supplierId;

    private BigDecimal price;

    private Integer publishStatus;

    private Integer auditStatus;

    private Integer saleNum;

    private String saleTime;

    private String describesion;

    private Integer weight;

    private Integer isExchange;

    private Integer productSort;

    private Integer isDealer;

    private Integer isCommission;

    private Integer templateId;

    private Integer threeCategoryId;

    private String shareImage;

    private String shareTitle;
}
