package com.vsj.model;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: HuaS
 * @Date :2019/7/25 17:57
 * @Describe:
 */
@Data
public class VsjMemberRelationSet implements Serializable {
    private static final long serialVersionUID = -3069660321865694970L;
    private Integer id;
    private Byte relationSwitch;
    private Integer needRequire;
    private Integer bindPhone;
    private BigDecimal expenseMoney;
    private Integer expenseNum;
    private String productIds;
    private Integer productNum;
    private Integer offlineConditions;
    private Integer platformCode;
}
