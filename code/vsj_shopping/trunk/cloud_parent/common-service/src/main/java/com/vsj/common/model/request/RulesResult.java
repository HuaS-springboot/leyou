package com.vsj.common.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname RulesResult
 * @Description TODO
 * @Date 2019/8/5 15:46
 * @Created by wangzx
 */
@Data
public class RulesResult implements Serializable {

    private static final long serialVersionUID = 4450611390376060227L;

    /**
     * 消费金额
     */
    private RulesMoney rulesMoney;
    /**
     * 消费笔数
     */
    private RulesOrderCount rulesOrderCount;
    /**
     * 商品消费
     */
    private RulesProductCount rulesProductCount;
    /**
     * 结算分红金额
     */
    private RulesDividend rulesDividend;
}
