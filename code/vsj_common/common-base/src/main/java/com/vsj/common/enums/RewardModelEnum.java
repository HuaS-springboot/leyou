package com.vsj.common.enums;

/**
 * 
 * @ClassName: RewardModelEnum
 * @Description: 奖励模式枚举
 * @author: mario 
 * @date: 2019年7月26日 下午3:11:04
 * @copyright: 青岛微视角文化传媒有限公司
 */
public enum RewardModelEnum {
	
	/**
	 * 固定金额
	 */
	FIXED(0,"固定金额"),
	/**
	 * 百分比
	 */
	PERCENTAGE(1,"百分比");
	
	private Integer code;
	
	private String msg;
	
	private RewardModelEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
