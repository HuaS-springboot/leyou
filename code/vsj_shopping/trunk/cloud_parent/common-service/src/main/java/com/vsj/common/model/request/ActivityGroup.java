package com.vsj.common.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ActivityGroup {
    private Integer id;
    private Integer activityId;
    private Integer productNumber;
    private Integer quotaNumber;
    private Integer groupNumber;
    private BigDecimal activityPrice;
    private Integer specsId;
    private String  createTime;
    private Integer platformCode;
    private BigDecimal productPrice;
    private String  attrJson;
    private String  productStock;
}
