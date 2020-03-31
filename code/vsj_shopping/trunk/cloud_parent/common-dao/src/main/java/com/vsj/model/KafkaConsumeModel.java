 package com.vsj.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
@ApiModel(value = "kafka消费记录表")
public class KafkaConsumeModel implements Serializable{

	private static final long serialVersionUID = -2392235119377167805L;
	
	@ApiModelProperty(name = "id",value = "ID",dataType = "Integer",required = false,example = "1")
	private Integer id;
	
	@ApiModelProperty(name = "topic",value = "主题",dataType = "String",required = false,example = "1")
	private String topic;
	
	@ApiModelProperty(name = "record",value = "记录内容",dataType = "String",required = false,example = "1")
	private String record;
	
	@ApiModelProperty(name = "status",value = "发送状态",dataType = "Integer",required = false,example = "1")
	private Integer status;
	
	@ApiModelProperty(name = "num",value = "发送次数",dataType = "Integer",required = false,example = "1")
	private Integer num;
	
	@ApiModelProperty(name = "createTime",value = "创建时间",dataType = "Date",required = false,example = "1")
	private Date createTime;
	
	@ApiModelProperty(name = "updateTime",value = "更新时间",dataType = "Date",required = false,example = "1")
	private Date updateTime;
	
	@ApiModelProperty(name = "consumerClass",value = "商品ID",dataType = "String",required = false,example = "1")
	private String consumerClass;

}
