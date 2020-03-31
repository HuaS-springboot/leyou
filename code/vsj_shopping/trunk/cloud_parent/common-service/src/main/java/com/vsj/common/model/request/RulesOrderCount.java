package com.vsj.common.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname RulesOrderCount
 * @Description TODO
 * @Date 2019/8/5 14:59
 * @Created by wangzx
 */
@Data
public class RulesOrderCount implements Serializable {

    private static final long serialVersionUID = 3900962594041590394L;

    /**
     * 满订单多少笔
     */
    private Integer orderCount;

    /**
     * 是否开启 0=不启用；1=启用
     */
    private Integer status;
}
