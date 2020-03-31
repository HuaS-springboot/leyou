package com.vsj.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname VsjProductSpecs
 * @Description TODO
 * @Date 2019/7/26 13:42
 * @Created by wangzx
 */
@Data
@ApiModel(value = "商品规格实体类")
public class VsjProductSpecs implements Serializable {

    private Integer id;

    @ApiModelProperty(name = "productId",value = "商品id",dataType = "Integer",example = "5")
    private Integer productId;

    @ApiModelProperty(name = "productStock",value = "库存",dataType = "Integer",example = "10")
    private Integer productStock;

    @ApiModelProperty(name = "productPrice",value = "实际销售价格",dataType = "Integer",example = "10")
    private Integer productPrice;

    @ApiModelProperty(name = "attrJson",value = "规格JSON数据",dataType = "String",example = "10")
    private String attrJson;

    @ApiModelProperty(name = "isnullSell",value = "无库存是否可以销售 1=是  2=否",dataType = "Integer",example = "10")
    private Integer isnullSell;

    @ApiModelProperty(name = "saleNum",value = "销量",dataType = "Integer",example = "10")
    private Integer saleNum;

    private Integer platformCode;
}
