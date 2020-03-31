package com.vsj.common.enums;

/**
 * 
 * @ClassName: DistributionLevelEnum
 * @Description: 分销等级枚举
 * @author: mario 
 * @date: 2019年7月25日 下午5:30:51
 * @copyright: 青岛微视角文化传媒有限公司
 */
public enum DistributionLevelEnum {
	
	/**
	 * 0,关闭
	 */
	CLOSE(0,"关闭"),
	/**
	 * 1,一级分销
	 */
	ONE(1,"一级分销"),
	/**
	 * 2,二级分销
	 */
	TWO(2,"二级分销");
	
	private Integer value;
	
	private String msg;
	
	private DistributionLevelEnum(Integer value, String msg) {
		this.value = value;
		this.msg = msg;
	}
	
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
