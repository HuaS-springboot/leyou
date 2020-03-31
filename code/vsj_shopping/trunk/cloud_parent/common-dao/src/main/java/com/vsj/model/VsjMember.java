package com.vsj.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class VsjMember implements Serializable {

    private static final long serialVersionUID = 1313442597685278971L;

    private Integer id;

    private Integer regId;

    private String regName;

    private String joinTime;

    private Integer levelId;
    
    private Integer parentId;

    private String parentName;
    
    private Integer platformCode;

}