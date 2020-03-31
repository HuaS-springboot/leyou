package com.vsj.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname VsjSaasSystem
 * @Description saas系统表
 * @Date 2019/7/31 15:08
 * @Created by wangzx
 */
@Data
public class VsjSaasSystem implements Serializable {

    private static final long serialVersionUID = 5028270587678422566L;

    private Integer id;
    private String saasName;
    private String saasDescribe;
    private String saasId;
    private Integer saasType;
    private String appId;
    private String appSecret;
    private String headPortrait;
    private String createTime;
    private Integer platformCode;
}
