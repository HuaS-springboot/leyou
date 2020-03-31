package com.vsj.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
public class VsjSysRole implements Serializable {
    private static final long serialVersionUID = -7795322716891960948L;
    private Integer id;

    private String roleCode;

    private String roleName;

    private Integer state;

    private Integer createId;

    private String createTime;

    private Integer platformCode;

    private Set<VsjSysPermission> permission;

}