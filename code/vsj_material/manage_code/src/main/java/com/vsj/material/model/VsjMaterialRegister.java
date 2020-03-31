package com.vsj.material.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname VsjMaterialRegister
 * @Description 注册
 * @Date 2019/8/14 16:19
 * @Created by wangzx
 */
@Data
public class VsjMaterialRegister implements Serializable {

    private static final long serialVersionUID = -3416298905747770209L;

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

    private Integer platformCode;
}
