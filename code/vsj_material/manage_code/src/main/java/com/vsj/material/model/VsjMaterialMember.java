package com.vsj.material.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname VsjMaterialMember
 * @Description TODO
 * @Date 2019/8/14 16:21
 * @Created by wangzx
 */
@Data
public class VsjMaterialMember implements Serializable {

    private static final long serialVersionUID = 5471525108856716139L;

    private Integer id;

    private Integer regId;

    private String joinTime;

    private Integer levelId;

    private String expiredTime;

    private String parentName;

    private Integer platformCode;
}
