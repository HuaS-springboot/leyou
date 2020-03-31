package com.vsj.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname VsjActivityGroup
 * @Description TODO
 * @Date 2019/7/29 16:54
 * @Created by zy
 */
@Data
public class VsjActivityGroup implements Serializable {

    private static final long serialVersionUID = -4517003770105001733L;
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
