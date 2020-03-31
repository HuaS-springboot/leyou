package com.vsj.common.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ActivitySeckill {
    private Integer id;

    private Integer activityId;

    private Integer productNumber;

    private Integer quotaNumber;

    private BigDecimal activityPrice;

    private Integer specsId;

    private String createTime;

    private Integer platformCode;

    private BigDecimal productPrice;

    private Integer productStock;

    private String attrJson;
}
