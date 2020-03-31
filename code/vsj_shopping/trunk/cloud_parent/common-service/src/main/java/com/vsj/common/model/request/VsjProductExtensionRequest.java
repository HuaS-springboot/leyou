package com.vsj.common.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname VsjProductExtensionRequest
 * @Description TODO
 * @Date 2019/8/2 14:07
 * @Created by wangzx
 */
@Data
@ApiModel(value = "商品扩展属性接受参数")
public class VsjProductExtensionRequest implements Serializable {

    private static final long serialVersionUID = 8757052982716574401L;

    private Integer id;

    @ApiModelProperty(name = "productId",value = "商品id",dataType = "Integer",required = true,example = "1")
    private Integer productId;

    @ApiModelProperty(name = "type",value = "类型 0=商品属性 ",dataType = "Integer",required = true,example = "1")
    private Integer type;

    @ApiModelProperty(name = "extensionKey",value = "扩展名称",dataType = "String",required = true)
    private String extensionKey;

    @ApiModelProperty(name = "extensionValue",value = "扩展值",dataType = "String",required = true)
    private String extensionValue;
}
