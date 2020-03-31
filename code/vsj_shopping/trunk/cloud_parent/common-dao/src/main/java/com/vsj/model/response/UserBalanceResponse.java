package com.vsj.model.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * @Classname
 * @Description
 * @Date  2019/8/26 15:13
 * @Created by HuaS
 */
@Data
public class UserBalanceResponse implements Serializable {

    private static final long serialVersionUID = -3194828728813794004L;
    private Integer id;
    private BigDecimal settledWages;//已结算提成
    private BigDecimal noSettledWages;//未结算提成
    private BigDecimal carryBalance;//余额、操作金额
    private BigDecimal freezeBalance;//冻结余额
}
