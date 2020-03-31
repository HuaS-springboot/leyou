package com.vsj.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class VsjMemberUpgradeRules implements Serializable {
    private Integer id;

    private Byte type;

    private Integer parentId;

    private String data;

    private Byte currentRelation;

    private Byte parentRelation;

    private Integer levelId;

    private Integer platformCode;
}