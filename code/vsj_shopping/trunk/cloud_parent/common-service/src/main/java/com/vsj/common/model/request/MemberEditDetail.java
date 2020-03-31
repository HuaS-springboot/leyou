package com.vsj.common.model.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class MemberEditDetail implements Serializable {
    private Integer regId;
    //register表信息
    private String name;
    private BigDecimal carryBalance;
    private String telPhone;
    private String remark;
    //member表信息
    private Integer id;
    private Integer parentId;
    private Integer levelId;
    private Integer platformCode;
}
