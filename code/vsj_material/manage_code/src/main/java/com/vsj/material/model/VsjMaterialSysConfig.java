package com.vsj.material.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname VsjMaterialSysConfig
 * @Description 素材小程序系统配置
 * @Date 2019/8/13 11:06
 * @Created by wangzx
 */
@Data
public class VsjMaterialSysConfig implements Serializable {

    private Integer id;
    private String key;
    private String value;
    private String remark;
    private Integer platformCode;
}
