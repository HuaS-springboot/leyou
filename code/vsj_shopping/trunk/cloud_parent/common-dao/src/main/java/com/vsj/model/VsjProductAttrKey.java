package com.vsj.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class VsjProductAttrKey implements Serializable {

    private static final long serialVersionUID = -6501932660276261064L;

    private Integer id;

    private String attrName;

    private Integer nameSort;

    private String createTime;

    private String updateTime;

    private Integer platformCode;
}