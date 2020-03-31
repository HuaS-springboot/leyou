package com.vsj.material.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname MaterialConversion
 * @Description 兑换码数据库model
 * @Date 2019/8/16 10:37
 * @Created by wangzx
 */
@Data
public class MaterialConversion implements Serializable {

    private Integer id;
    private String code;
    private Integer levelId;
    private String effectiveTime;
    private String invalidTime;
    private Integer levelDay;
    private Integer status;
    private String createTime;
    private Integer useMemberId;
    private String useTime;
    private Integer platformCode;
}
