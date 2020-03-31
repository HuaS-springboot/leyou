package com.vsj.material.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname SysConfig
 * @Description 系统配置参数接受
 * @Date 2019/8/13 15:00
 * @Created by wangzx
 */
@Data
public class SysConfig implements Serializable {

    private Integer id;

    private String key;

    private String value;

    private String remark;
}
