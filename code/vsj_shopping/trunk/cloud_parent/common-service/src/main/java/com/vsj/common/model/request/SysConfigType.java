package com.vsj.common.model.request;


import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "系统配置查询")
public class SysConfigType implements Serializable{
	
	private static final long serialVersionUID = 2497041138399957412L;
	
	@ApiModelProperty(name = "type",value = "系统配置类型",dataType = "Byte")
	private Byte type;
}
