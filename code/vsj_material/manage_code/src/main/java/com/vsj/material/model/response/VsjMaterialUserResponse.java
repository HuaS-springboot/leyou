package com.vsj.material.model.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname VsjMaterialUserResponse
 * @Description 会员列表查询返回
 * @Date 2019/8/14 10:08
 * @Created by wangzx
 */
@Data
public class VsjMaterialUserResponse implements Serializable {

    private Integer id;
    private String nickName;
    private String telPhone;
    private String headUrl;
    private String createTime;
    private String joinTime;
    private Integer levelId;
    private String expiredTime;
    private Integer platformCode;
    private String levelName;
}
