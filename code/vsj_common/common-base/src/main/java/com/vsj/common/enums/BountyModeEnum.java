package com.vsj.common.enums;

/**
 * 
 * @ClassName: BountyModeEnum
 * @Description: 分红模式枚举
 * @author: mario 
 * @date: 2019年7月26日 下午3:10:38
 * @copyright: 青岛微视角文化传媒有限公司
 */
public enum BountyModeEnum {
	
	/**
	 * 0,关闭
	 */
	CLOSE(0,"关闭"),
	/**
	 * 1,分销模式
	 */
	DISTRIBUTION(1,"分销模式"),
	/**
	 * 2,经销商极差模式
	 */
	RANGE(2,"经销商极差模式");
	
	private Integer code;
	
	private String msg;
	
	private BountyModeEnum(Integer code, String msg) {
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
