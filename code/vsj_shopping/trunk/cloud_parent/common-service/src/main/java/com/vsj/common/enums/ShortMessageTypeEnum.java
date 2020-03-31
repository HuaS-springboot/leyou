package com.vsj.common.enums;

/**
 * 
 * @ClassName: ShortMessageTypeEnum
 * @Description: 短信类型枚举
 * @author: mario 
 * @date: 2019年7月30日 下午7:06:13
 * @copyright: 青岛微视角文化传媒有限公司
 */
public enum ShortMessageTypeEnum {
	
	/**
	 * 1,注册
	 */
	REGISTE((byte)1,"注册"),
	/**
	 * 2,找回密码
	 */
	FIND((byte)2,"找回密码");
	
	private Byte code;
	
	private String msg;
	
	private ShortMessageTypeEnum(Byte code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public Byte getCode() {
		return code;
	}

	public void setCode(Byte code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
