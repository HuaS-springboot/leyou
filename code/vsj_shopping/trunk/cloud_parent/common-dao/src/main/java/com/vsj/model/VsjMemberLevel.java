package com.vsj.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: HuaS
 * @Date :2019/7/24 14:38
 * @Describe:
 */
@Data
public class VsjMemberLevel implements Serializable {
    private static final long serialVersionUID = 3324148070157681416L;
    private Integer id;
    private String levelName;
    private Integer sort;
    private Integer isDefault;
    private Integer platformCode;
}
