package com.vsj.common.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname RulesMoney
 * @Description TODO
 * @Date 2019/8/5 14:55
 * @Created by wangzx
 */
@Data
public class RulesMoney implements Serializable {

    /**
     * 满多少元
     */
    private Double totalMoney;

    /**
     * 是否开启 0=不启用；1=启用
     */
    private Integer status;
}
