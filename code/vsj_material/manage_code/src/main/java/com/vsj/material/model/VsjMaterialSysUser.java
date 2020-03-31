package com.vsj.material.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class VsjMaterialSysUser implements Serializable {
    private Integer id;
    private String userName;
    private String nickName;
    private String phone;
    private String sex;
    private String imgUrl;
    private String pswd;
    private String email;
    private Integer state;
    private Date createTime;
    private Integer createId;
    private Integer modifier;
    private Date lastModifyTime;
    private String remark;
    private Integer platformCode;
}
