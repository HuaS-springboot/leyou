package com.vsj.model;

import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class VsjRegister implements Serializable {
    private static final long serialVersionUID = -1988369492978927776L;
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

    private BigDecimal carryBalance;

    private BigDecimal freezeBalance;

    private String remark;

    private Integer platformCode;

    @Transient
    private String  token;
    @Transient
    private Integer status;
}
