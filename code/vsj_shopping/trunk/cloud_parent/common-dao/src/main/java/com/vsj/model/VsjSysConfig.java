package com.vsj.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "系统配置信息")
public class VsjSysConfig implements Serializable {
	@ApiModelProperty(name = "id",value = "id",dataType = "Integer",required = true,example = "1")
    private Integer id;
	@ApiModelProperty(name = "key",value = "键",dataType = "String",required = true,example = "1")
    private String key;
	@ApiModelProperty(name = "value",value = "值",dataType = "String",required = true,example = "1")
    private String value;
	@ApiModelProperty(name = "remark",value = "备注",dataType = "String",required = true,example = "1")
    private String remark;
	@ApiModelProperty(name = "platformCode",value = "平台码",dataType = "Integer",required = true,example = "1")
    private Integer platformCode;

}