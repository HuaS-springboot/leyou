package com.vsj.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname VsjProductExtension
 * @Description TODO
 * @Date 2019/7/24 18:17
 * @Created by wangzx
 */
@Data
@ApiModel(value = "商品扩展属性表")
public class VsjProductExtension implements Serializable {

    private Integer id;

    @ApiModelProperty(name = "productId",value = "商品id",dataType = "Integer",required = true,example = "1")
    private Integer productId;

    @ApiModelProperty(name = "type",value = "类型 0=商品属性 ",dataType = "Integer",required = true,example = "1")
    private Integer type;

    @ApiModelProperty(name = "extensionKey",value = "扩展名称",dataType = "String",required = true)
    private String extensionKey;

    @ApiModelProperty(name = "extensionValue",value = "扩展值",dataType = "String",required = true)
    private String extensionValue;

    private Integer platformCode;

    private String createTime;

}
