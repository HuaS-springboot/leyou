package com.vsj.common.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname ProAttrList
 * @Description TODO
 * @Date 2019/7/25 17:33
 * @Created by wangzx
 */
@Data
@ApiModel(value = "多重添加规格实体类")
public class ProAttrList {

    private Integer id;

    @ApiModelProperty(name = "attrName",value = "规格名称",dataType = "String")
    private String attrName;

    @ApiModelProperty(name = "nameSort",value = "属性名排序",dataType = "Integer",example = "1")
    private Integer nameSort;

    @ApiModelProperty(name = "attrValueList",value = "对应规格名称多个值",dataType = "List")
    List<VsjProductAttrValueRequest> attrValueList = new ArrayList<>();

    private Integer platformCode;
}
