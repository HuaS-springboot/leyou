package com.vsj.material.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname VsjMaterialPackage
 * @Description TODO
 * @Date 2019/8/20 17:10
 * @Created by wangzx
 */
@Data
public class VsjMaterialPackage implements Serializable {

    private Integer id;
    private Integer status;
    private Integer levelId;
    private Integer day;
    private BigDecimal money;
    private Integer platformCode;
}
