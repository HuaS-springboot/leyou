package com.vsj.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class VsjSysUserRole implements Serializable {
    private static final long serialVersionUID = 2070206314170789431L;
    private Integer id;

    private Integer uid;

    private Integer rid;

    private Integer platformCode;
}