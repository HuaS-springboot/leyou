package com.vsj.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class VsjOrderOperation implements Serializable {

    private static final long serialVersionUID = 1764112883802514995L;

    private Integer id;

    private Integer orderId;

    private Integer operateType;

    private String creteTime;

    private String remark;

    private Integer regId;

    private Integer sysUserId;

    private Integer platformCode;

    private  String nickName;//注册名

    private String nickNameSys;//系统用户名
}