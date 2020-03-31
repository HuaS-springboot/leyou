package com.vsj.material.model.response;

import lombok.Data;

import java.io.Serializable;
@Data
public class MaterialUserLevelResponse implements Serializable {
    private Integer id;
    private String levelName;
    private Integer sort;
    private Integer isDefault;
    private Integer platformCode;
}
