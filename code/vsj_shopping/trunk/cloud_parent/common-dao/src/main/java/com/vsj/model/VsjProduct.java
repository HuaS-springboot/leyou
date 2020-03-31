package com.vsj.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Classname VsjProduct
 * @Description 商品实体类
 * @Date 2019/7/24 13:36
 * @Created by wangzx
 */
@ApiModel(value = "商品实体类")
@Data
public class VsjProduct implements Serializable {

    @ApiModelProperty(name = "productId",value = "商品ID",dataType = "Integer",required = false,example = "1")
    private Integer productId;

    @ApiModelProperty(name = "productCode",value = "商品code",dataType = "String",required = false)
    private String productCode;

    @ApiModelProperty(name = "productName",value = "商品名称",dataType = "String",required = false)
    private String productName;

    @ApiModelProperty(name = "productImage",value = "商品图片",dataType = "String",required = false)
    private String productImage;

    @ApiModelProperty(name = "brandId",value = "品牌ID",dataType = "Integer",required = false,example = "1")
    private Integer brandId;

    @ApiModelProperty(name = "oneCategoryId",value = "一级分类ID",dataType = "Integer",required = false,example = "1")
    private Integer oneCategoryId;

    @ApiModelProperty(name = "twoCategoryId",value = "二级分类ID",dataType = "Integer",required = false,example = "1")
    private Integer twoCategoryId;

    @ApiModelProperty(name = "supplierId",value = "商品的供应商ID",dataType = "Integer",required = false,example = "1")
    private Integer supplierId;

    @ApiModelProperty(name = "price",value = "商品销售定价",dataType = "BigDecimal",required = false,example = "1")
    private BigDecimal price;

    @ApiModelProperty(name = "publishStatus",value = "上下架状态：0下架1上架",dataType = "Integer",required = false,example = "1")
    private Integer publishStatus;

    @ApiModelProperty(name = "auditStatus",value = "审核状态：0未审核，1已审核",dataType = "Integer",required = false,example = "1")
    private Integer auditStatus;

    @ApiModelProperty(name = "saleNum",value = "总销售量",dataType = "Integer",required = false,example = "1")
    private Integer saleNum;

    @ApiModelProperty(name = "saleTime",value = "商品起售时间",dataType = "String",required = false)
    private String saleTime;

    @ApiModelProperty(name = "describesion",value = "商品描述",dataType = "String",required = false)
    private String describesion;

    @ApiModelProperty(name = "weight",value = "商品重量",dataType = "Integer",required = false,example = "1")
    private Integer weight;

    @ApiModelProperty(name = "isExchange",value = "是否支持退换货   0=不支持  1=支持",dataType = "Integer",required = false,example = "1")
    private Integer isExchange;

    @ApiModelProperty(name = "productSort",value = "排序",dataType = "Integer",required = false,example = "1")
    private Integer productSort;

    @ApiModelProperty(name = "isDealer",value = "是否开启经销商提成开关  0=关  1=开",dataType = "Integer",required = false,example = "1")
    private Integer isDealer;

    @ApiModelProperty(name = "isCommission",value = "是否开启独立提成  0=关  1=开",dataType = "Integer",required = false,example = "1")
    private Integer isCommission;

    @ApiModelProperty(name = "templateId",value = "配送模板id",dataType = "Integer",required = false,example = "1")
    private Integer templateId;

    private String createTime;

    private String modifiedTime;

    private Integer platformCode;

    private Integer totalStock;

    private Integer threeCategoryId;

    private String shareImage;

    private String shareTitle;


}
