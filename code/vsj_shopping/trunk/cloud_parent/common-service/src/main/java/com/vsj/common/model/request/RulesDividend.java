package com.vsj.common.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname RulesDividend
 * @Description TODO
 * @Date 2019/8/5 15:03
 * @Created by wangzx
 */
@Data
public class RulesDividend implements Serializable {

    private static final long serialVersionUID = -7175737756662225355L;

    /**
     * 结算分红奖励满多少元
     */
    private double dividendMoney;

    /**
     * 是否开启 0=不启用；1=启用
     */
    private Integer status;
}
