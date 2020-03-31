package com.vsj.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class VsjSysPermission implements Serializable {
    private Integer id;

    private String url;

    private String name;

    private Integer parentId;

    private String icon;

    private String code;

    private Integer platformCode;

    private List<VsjSysPermission> children;
}