 package com.vsj.model;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
@ApiModel(value = "kafka发送记录表")
public class KafkaSendModel implements Serializable{

	private static final long serialVersionUID = 7692620120431099093L;
	
	@ApiModelProperty(name = "id",value = "ID",dataType = "String",required = false,example = "1")
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
	
	@ApiModelProperty(name = "itemId",value = "业务ID",dataType = "Integer",required = false,example = "1")
	private Integer itemId;
	
	@ApiModelProperty(name = "key",value = "key",dataType = "String",required = false,example = "1")
	private String key;
	
	public KafkaSendModel(){
		
	}
	
	public KafkaSendModel(String topic,Object record){
		this.topic = topic;
		this.record = JSON.toJSONString(record);
	}
	
	public KafkaSendModel(String topic,Object record,String key){
		this.topic = topic;
		this.record = JSON.toJSONString(record);
		this.key = key;
	}
	
	public KafkaSendModel(String topic,Object record,Integer itemId){
		this.topic = topic;
		this.record = JSON.toJSONString(record);
		this.itemId = itemId;
	}
	
	public KafkaSendModel(String topic,Object record,Integer itemId,String key){
		this.topic = topic;
		this.record = JSON.toJSONString(record);
		this.itemId = itemId;
		this.key = key;
	}

}
