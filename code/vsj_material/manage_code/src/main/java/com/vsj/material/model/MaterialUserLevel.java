package com.vsj.material.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MaterialUserLevel implements Serializable {
    private Integer id;
    private String levelName;
    private Integer sort;
    private Integer isDefault;
    private Integer platformCode;
}
