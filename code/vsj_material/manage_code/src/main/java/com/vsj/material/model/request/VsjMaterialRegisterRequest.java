package com.vsj.material.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class VsjMaterialRegisterRequest implements Serializable {
    private static final long serialVersionUID = -7372724558662501464L;
    private Integer id;

    private String openId;

    private String nickName;

    private String headUrl;

    private Integer sex;

    private String country;

    private String province;

    private String city;

    private String area;

    private String language;

    private String createTime;

    private String telPhone;

    private String type;

    private Double carryBalance;

    private Double freezeBalance;

    private String remark;

}
