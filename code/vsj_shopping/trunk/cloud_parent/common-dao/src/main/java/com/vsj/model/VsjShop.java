package com.vsj.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class VsjShop implements Serializable {
    private static final long serialVersionUID = -6364505355677285289L;
    private Integer shopId;

    private String shopCode;

    private String shopName;

    private Byte shopType;

    private String linkMan;

    private String phoneNumber;

    private String bankName;

    private String bankAccount;

    private String address;

    private Byte shopStatus;

    private Date modifiedTime;

    private Integer platformCode;
}