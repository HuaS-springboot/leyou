package com.vsj.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname VsjStageSchema
 * @Description TODO
 * @Date 2019/7/26 16:37
 * @Created by wangzx
 */
@Data
@ApiModel(value = "级差模式奖励设置表实体类")
public class VsjStageSchema implements Serializable {

    private Integer id;

    @ApiModelProperty(name = "rankId",value = "等级ID",dataType = "Integer",example = "5")
    private Integer levelId;

    @ApiModelProperty(name = "openPeers",value = "是否开启平级奖励  0=否 1=是",dataType = "Integer",example = "5")
    private Integer openPeers;

    @ApiModelProperty(name = "bonusNum",value = "提成比例",dataType = "Integer",example = "5")
    private Integer bonusNum;

    @ApiModelProperty(name = "bonusUnits",value = "提成比例单位  0=百分比  1=元",dataType = "Integer",example = "5")
    private Integer bonusUnits;

    @ApiModelProperty(name = "peersNum",value = "平级比例",dataType = "Integer",example = "5")
    private Integer peersNum;

    @ApiModelProperty(name = "peersHierarchy",value = "平级层级",dataType = "Integer",example = "5")
    private Integer peersHierarchy;

    @ApiModelProperty(name = "perrsUnits",value = "平级提成比例单位  0=百分比  1=元",dataType = "Integer",example = "5")
    private Integer perrsUnits;

    @ApiModelProperty(name = "productId",value = "商品ID，注：有商品ID的情况下说明该商品配置了独立的奖励模式",dataType = "Integer",example = "5")
    private Integer productId;

    private Integer platformCode;

    private String levelName;

}
