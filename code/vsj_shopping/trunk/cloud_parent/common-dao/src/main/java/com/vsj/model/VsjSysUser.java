package com.vsj.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
public class VsjSysUser implements Serializable {
    private static final long serialVersionUID = 2824133732906221350L;
    private Integer id;

    private String account;
    
    private String nickName;

    private String phone;

    private Boolean sex;

    private String imgUrl;

    private String pswd;

    private String email;

    private Boolean state;

    private Integer createId;

    private String createTime;

    private Integer modifier;

    private String lastModifyTime;

    private String remark;

    private Integer platformCode;

    private Set<VsjSysRole> roles;
}