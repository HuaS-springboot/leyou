package com.vsj.common.model.request;

import com.vsj.model.VsjProductCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HuaS
 * @Date :2019/8/2 12:46
 * @Describe:
 */
@Data
public class ProductCategory {
    private Integer id;
    private  Integer typeNum;
    @ApiModelProperty(name = "categoryName",value = "分类名称",dataType = "String")
    private String categoryName;
    private String categoryCode;
    @ApiModelProperty(name = "parentId",value = "父id",dataType = "Integer",example = "1")
    private Integer parentId;
    @ApiModelProperty(name = "categorySort",value = "分类权重",dataType = "Integer",example = "1")
    private Integer categorySort;
    @ApiModelProperty(name = "categoryStatus",value = "分类状态",dataType = "Integer",example = "1")
    private Integer categoryStatus;
    private String modifiedTime;
    @ApiModelProperty(name = "ico",value = "分类图片",dataType = "String")
    private String  ico;
    private Integer platformCode;
    @ApiModelProperty(name = "children",value = "对应分类名称多个值",dataType = "List")
    private List<VsjProductCategory> children = new ArrayList<>();
}
