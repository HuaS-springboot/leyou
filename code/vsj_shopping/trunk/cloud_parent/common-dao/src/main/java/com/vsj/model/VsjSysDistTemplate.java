package com.vsj.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname VsjSysDistTemplate
 * @Description TODO
 * @Date 2019/7/26 9:26
 * @Created by wangzx
 */
@Data
@ApiModel(value = "配送模板实体类")
public class VsjSysDistTemplate implements Serializable {
    private Integer id;

    @ApiModelProperty(name = "status",value = "状态 0=不是默认；1=默认",dataType = "Integer",example = "0")
    private Integer status;

    private String configuration;

    private Integer platformCode;

}
