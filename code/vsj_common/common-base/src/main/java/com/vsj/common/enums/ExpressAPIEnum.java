package com.vsj.common.enums;

/**
 * 
 * @ClassName: ExpressAPIEnum
 * @Description: 快递API枚举
 * @author: mario 
 * @date: 2019年7月30日 下午5:06:28
 * @copyright: 青岛微视角文化传媒有限公司
 */
public enum ExpressAPIEnum {

	/**
	 * 0,关闭,未配置
	 */
	CLOSE(0,"关闭"),
	/**
	 * 1,快递鸟
	 */
	KDN(1,"快递鸟");

	private Integer code;

	private String msg;

	private ExpressAPIEnum(Integer code, String msg) {
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
