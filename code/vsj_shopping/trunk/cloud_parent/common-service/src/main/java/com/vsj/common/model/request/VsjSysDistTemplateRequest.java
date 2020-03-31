package com.vsj.common.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname VsjSysDistTemplateRequest
 * @Description TODO
 * @Date 2019/8/2 14:20
 * @Created by wangzx
 */
@Data
public class VsjSysDistTemplateRequest implements Serializable {

    private static final long serialVersionUID = -6142664268339923613L;

    private Integer id;

    private String configuration;
}
