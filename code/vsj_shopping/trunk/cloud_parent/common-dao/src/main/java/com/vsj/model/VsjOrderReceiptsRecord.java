package com.vsj.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
@ApiModel(value = "会员订单收益明细")
public class VsjOrderReceiptsRecord {
	
	@ApiModelProperty(name = "id",value = "id",dataType = "Integer",example = "1")
    private Integer id;
	@ApiModelProperty(name = "orderId",value = "订单id",dataType = "Integer",example = "1")
    private Integer orderId;
	@ApiModelProperty(name = "memberId",value = "会员id",dataType = "Integer",example = "1")
    private Integer memberId;
	@ApiModelProperty(name = "operationMoney",value = "操作金额",dataType = "BigDecimal",example = "1")
    private BigDecimal operationMoney;
	@ApiModelProperty(name = "createTime",value = "操作时间",dataType = "Date",example = "1")
    private String createTime;
	@ApiModelProperty(name = "type",value = "类型：0=收入  1=支出",dataType = "Byte",example = "0")
    private Byte type;
	@ApiModelProperty(name = "incomeSource",value = "收入来源",dataType = "Byte",example = "0")
	private Byte incomeSource;
	@ApiModelProperty(name = "platformCode",value = "平台编码",dataType = "Integer",example = "0")
	private Integer platformCode;

	private String levelName;

	private String nickName;
	private String parentName;
	private String joinTime;
	private Integer parentId;
	private String telPhone;
	private Double carryBalance;
	private String remark;
	private Integer levelId;
	private Integer isSettle;

	private String orderNo;
	private Integer orderStatus;
	private BigDecimal orderAmountTotal;
	private String orderTime;

	public VsjOrderReceiptsRecord(){
		
	}
	
	public VsjOrderReceiptsRecord(Integer orderId,Integer memberId,BigDecimal operationMoney,Byte type,Byte incomeSource,Integer platformCode,
								  Integer levelId){
		this.orderId = orderId;
		this.memberId = memberId;
		this.operationMoney = operationMoney;
		this.type = type;
		this.incomeSource = incomeSource;
		this.platformCode = platformCode;
		this.levelId = levelId;
	}
}