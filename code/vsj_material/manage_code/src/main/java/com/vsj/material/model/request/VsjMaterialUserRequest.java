package com.vsj.material.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname VsjMaterialUserRequest
 * @Description 会员参数接受类
 * @Date 2019/8/14 16:02
 * @Created by wangzx
 */
@Data
public class VsjMaterialUserRequest implements Serializable {

    private static final long serialVersionUID = -7282145908585990080L;

    private Integer id;
    private String headUrl;
    private String nickName;
    private String createTime;
    private Integer levelId;
    private String expiredTime;
    private String telPhone;
    private Integer platformCode;
}
