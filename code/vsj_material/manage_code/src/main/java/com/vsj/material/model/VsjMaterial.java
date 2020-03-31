package com.vsj.material.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class VsjMaterial implements Serializable {

    private static final long serialVersionUID = 101380673052316781L;
    private Integer id;
    private String title;
    private String image;
    private String content;
    private Integer sort;
    private Integer collectionNum;
    private Integer downloadNum;
    private Integer sysUsrId;
    private Integer status;
    private Integer oneCateId;
    private Integer twoCateId;
    private Integer threeCateId;
    private Integer platformCode;
    private String  nickName;
    private Integer resourceType;


}
