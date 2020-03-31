package com.vsj.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class VsjRegisterAddress implements Serializable {

    private static final long serialVersionUID = 2302186046362141352L;

    private Integer addressId;

    private Integer regId;

    private String realname;

    private String telphone;

    private String telphone2;

    private String country;

    private String province;

    private String city;

    private String area;

    private String street;

    private String zip;

    private Integer isDefaultAddress;

    private String createTime;

    private Integer platformCode;
}