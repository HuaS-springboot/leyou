package com.vsj.model.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname ParentDist
 * @Description TODO
 * @Date 2019/8/20 13:44
 * @Created by wangzx
 */
@Data
public class ParentDist implements Serializable {

    /**
     *model = 0 首件；model = 1 首重多少
     */
    private Integer firstHeavy;

    /**
     * model = 0 首件多钱；model = 1 首重多钱
     */
    private Integer firstHeavyCost;

    /**
     * 计费方式 0=按件计费；1=按重量计费
     */
    private Integer model;

    /**
     * model = 0 续件 续xxx件；model = 1 续重 续xxx
     */
    private Integer nextHeavy;

    /**
     * model = 0 续件多少钱；model= 1续重多少钱
     */
    private Integer nextHeavyCost;

    /**
     * 配送模板名称
     */
    private String name;

    /**
     * 省级code码
     */
    private String provinceCode;

    /**
     * 市级code码
     */
    private String cityCode;
}
