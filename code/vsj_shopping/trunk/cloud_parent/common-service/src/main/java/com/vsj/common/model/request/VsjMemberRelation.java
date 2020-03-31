package com.vsj.common.model.request;

import lombok.Data;

import java.math.BigDecimal;
/**
 * @Author: HuaS
 * @Date :2019/8/2 10:27
 * @Describe:
 */
@Data
public class VsjMemberRelation {//com.vsj.common.model.request.VsjMemberRelation
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
