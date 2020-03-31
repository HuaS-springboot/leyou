package com.vsj.common.model.request;

import java.io.Serializable;
import java.util.List;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "系统配置列表")
public class SysConfigList implements Serializable{
	
	private static final long serialVersionUID = -2861136760539163338L;
	
	@ApiModelProperty(name = "vsjSysConfigList",value = "系统配置列表",dataType = "List")
	private List<SysConfig> vsjSysConfigList ;
}
