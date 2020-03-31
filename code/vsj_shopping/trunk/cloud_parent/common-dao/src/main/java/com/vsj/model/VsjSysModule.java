package com.vsj.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class VsjSysModule implements Serializable {
    private Integer id;

    private Integer parentId;

    private String name;

    private Integer platformCode;
}