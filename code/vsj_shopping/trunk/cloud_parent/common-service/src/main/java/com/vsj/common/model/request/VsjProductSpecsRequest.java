package com.vsj.common.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname VsjProductSpecsRequest
 * @Description 商品规格参数接受类
 * @Date 2019/8/2 14:51
 * @Created by wangzx
 */
@Data
@ApiModel(value = "商品规格参数接受类")
public class VsjProductSpecsRequest implements Serializable {

    private static final long serialVersionUID = -7436744960470635921L;

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
}
