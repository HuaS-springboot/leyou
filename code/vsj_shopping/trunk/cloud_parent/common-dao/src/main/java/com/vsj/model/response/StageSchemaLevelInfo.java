package com.vsj.model.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "级差模式对应等级奖励")
public class StageSchemaLevelInfo implements Serializable {

	private static final long serialVersionUID = -5161183394412368589L;
	
	@ApiModelProperty(name = "id", value = "id", dataType = "Integer", example = "1")
	private Integer id;
	 
	@ApiModelProperty(name = "openPeers", value = "是否开启平级奖励  0=否 1=是", dataType = "Integer", example = "5")
	private Integer openPeers;

	@ApiModelProperty(name = "bonusNum", value = "提成比例", dataType = "Integer", example = "5")
	private Integer bonusNum;

	@ApiModelProperty(name = "bonusUnits", value = "提成比例单位  0=百分比  1=元", dataType = "Integer", example = "5")
	private Integer bonusUnits;

	@ApiModelProperty(name = "peersNum", value = "平级比例", dataType = "Integer", example = "5")
	private Integer peersNum;

	@ApiModelProperty(name = "peersHierarchy", value = "平级层级", dataType = "Integer", example = "5")
	private Integer peersHierarchy;

	@ApiModelProperty(name = "perrsUnits", value = "平级提成比例单位  0=百分比  1=元", dataType = "Integer", example = "5")
	private Integer perrsUnits;

	@ApiModelProperty(name = "productId", value = "商品ID，注：有商品ID的情况下说明该商品配置了独立的奖励模式", dataType = "Integer", example = "5")
	private Integer productId;
	
	@ApiModelProperty(name = "levelId", value = "等级ID", dataType = "Integer", example = "5")
	private Integer levelId;
	
	@ApiModelProperty(name = "levelName", value = "等级名称", dataType = "Integer", example = "5")
	private String levelName;
	
	@ApiModelProperty(name = "levelName", value = "等级权重", dataType = "Integer", example = "1")
	private Integer sort;
	
	@ApiModelProperty(name = "levelName", value = "是否是默认 0=不是默认；1=是默认", dataType = "Integer", example = "0")
	private Integer isDefault;
	
	@ApiModelProperty(name = "platformCode", value = "平台码", dataType = "Integer", example = "5")
	private Integer platformCode;
}
