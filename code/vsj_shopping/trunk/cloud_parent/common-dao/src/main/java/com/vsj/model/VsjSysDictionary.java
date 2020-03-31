package com.vsj.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
@ApiModel(value = "系统数据字典")
public class VsjSysDictionary implements Serializable {

	private static final long serialVersionUID = -3953823945891411261L;
	@ApiModelProperty(name = "id",value = "id",dataType = "Integer",required = true,example = "1")
	private Integer id;
	@ApiModelProperty(name = "type",value = "类型",dataType = "String",required = true,example = "1")
	private String type;
	@ApiModelProperty(name = "key",value = "键",dataType = "String",required = true,example = "1")
	private String key;
	@ApiModelProperty(name = "value",value = "值",dataType = "String",required = true,example = "1")
	private String value;
	@ApiModelProperty(name = "remark",value = "描述",dataType = "String",required = true,example = "1")
	private String desc;
	
}
