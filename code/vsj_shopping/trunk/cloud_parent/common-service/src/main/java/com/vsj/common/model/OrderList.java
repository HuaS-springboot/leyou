package com.vsj.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname OrderList
 * @Description TODO
 * @Date 2019/8/2 16:45
 * @Created by zy
 */
@Data
public class OrderList implements Serializable {
    /**
     * 订单集合
     */
    private List<Order> orders;
}
