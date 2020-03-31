package com.vsj.material.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class MaterialUserLevelRequest implements Serializable {
    private Integer id;
    private String levelName;
    private Integer sort;
    private Integer isDefault;
    private Integer platformCode;
}
