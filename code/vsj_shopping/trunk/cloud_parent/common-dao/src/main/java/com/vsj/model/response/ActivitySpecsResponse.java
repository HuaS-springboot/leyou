package com.vsj.model.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname ActivitySpecsResponse
 * @Description TODO
 * @Date 2019/8/22 11:12
 * @Created by zy
 */
@Data
public class ActivitySpecsResponse implements Serializable {
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
    private Integer activityType;
}
