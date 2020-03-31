package com.vsj.common.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: HuaS
 * @Date :2019/8/8 11:14
 * @Describe:
 */
@Data
public class MaterialL {
    private Integer id;
    @ApiModelProperty(name = "content",value = "素材内容",dataType = "String")
    private String content;
    @ApiModelProperty(name = "img",value = "素材图片",dataType = "String")
    private String img;
    @ApiModelProperty(name = "sort",value = "父id",dataType = "Integer",example = "1")
    private Integer sort;
    private Integer productId;
    private Integer platformCode;
    private String createTime;
//    @ApiModelProperty(name = "ids",value = "批量id",dataType = "List")
//    private List<Integer> ids;
//    @ApiModelProperty(name = "sorts",value = "批量权重",dataType = "List")
//    private List<Integer> sorts;

}
