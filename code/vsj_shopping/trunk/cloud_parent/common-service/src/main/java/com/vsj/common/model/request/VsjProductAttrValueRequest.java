package com.vsj.common.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname VsjProductAttrValueRequest
 * @Description TODO
 * @Date 2019/8/2 15:18
 * @Created by wangzx
 */
@Data
public class VsjProductAttrValueRequest implements Serializable {

    private static final long serialVersionUID = 6313084523740889565L;

    private Integer id;

    @ApiModelProperty(name = "attrId",value = "规格id",dataType = "Integer",example = "1")
    private Integer attrId;

    @ApiModelProperty(name = "attrValue",value = "规格值对应的value",dataType = "String")
    private String attrValue;

    @ApiModelProperty(name = "valueSort",value = "规格值怕排序",dataType = "Integer",example = "1")
    private Integer valueSort;
}
