package com.vsj.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class VsjProductBrand implements Serializable {

    private static final long serialVersionUID = 8801333788981918382L;

    private Integer brandId;

    private String brandName;

    private String telephone;

    private String brandWeb;

    private String brandLogo;

    private String brandDesc;

    private Integer brandStatus;

    private Integer brandOrder;

    private String modifiedTime;

    private Integer platformCode;
}