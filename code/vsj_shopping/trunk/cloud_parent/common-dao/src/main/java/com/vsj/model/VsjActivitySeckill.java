package com.vsj.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class VsjActivitySeckill implements Serializable {

    private static final long serialVersionUID = 5678225028431247788L;
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