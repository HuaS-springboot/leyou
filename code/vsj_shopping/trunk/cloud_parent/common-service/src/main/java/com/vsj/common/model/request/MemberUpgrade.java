package com.vsj.common.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class MemberUpgrade implements Serializable {
    private Integer id;
    private Byte type;
    private Integer parentId;
    private RulesResult data;
    private Byte currentRelation;
    private Byte parentRelation;
    private Integer levelId;
    private Byte action;
}
