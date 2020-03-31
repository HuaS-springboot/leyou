package com.vsj.material.model.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class MaterialMemberPackageRequest implements Serializable {
    private Integer id;
    private Integer status;
    private Integer levelId;
    private Integer day;
    private BigDecimal money;
}
