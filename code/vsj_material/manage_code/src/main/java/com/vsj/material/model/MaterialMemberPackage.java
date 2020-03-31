package com.vsj.material.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
public class MaterialMemberPackage implements Serializable {
    private Integer id;
    private Integer status;
    private Integer levelId;
    private Integer day;
    private BigDecimal money;
    private Integer platformCode;
}
