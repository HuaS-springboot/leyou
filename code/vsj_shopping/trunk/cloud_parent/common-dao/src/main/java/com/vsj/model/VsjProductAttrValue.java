package com.vsj.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname VsjProductAttrValue
 * @Description TODO
 * @Date 2019/7/25 17:53
 * @Created by wangzx
 */
@Data
@ApiModel(value = "多重添加规格值实体类")
public class VsjProductAttrValue implements Serializable {

    private Integer id;

    @ApiModelProperty(name = "attrId",value = "规格id",dataType = "Integer",example = "1")
    private Integer attrId;

    @ApiModelProperty(name = "attrValue",value = "规格值对应的value",dataType = "String")
    private String attrValue;

    @ApiModelProperty(name = "valueSort",value = "规格值怕排序",dataType = "Integer",example = "1")
    private Integer valueSort;

    private String createTime;
    private String updateTime;
    private Integer platformCode;
}
