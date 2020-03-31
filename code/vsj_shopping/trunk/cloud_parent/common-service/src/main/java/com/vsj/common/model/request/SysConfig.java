package com.vsj.common.model.request;


import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "系统配置信息")
public class SysConfig implements Serializable{
	
	private static final long serialVersionUID = 1660143832916927155L;

	@ApiModelProperty(name = "key",value = "键",dataType = "String",required = true,example = "1")
	private String key;
	
	@ApiModelProperty(name = "value",value = "值",dataType = "String",required = true,example = "1")
	private String value;
}
