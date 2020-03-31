package com.vsj.common.enums;

/**
 * 
 * @ClassName: ResponseCodeEnum
 * @Description: 返回码枚举类
 * @author: mario 
 * @date: 2019年7月20日 下午8:55:36
 * @copyright: 青岛微视角文化传媒有限公司
 */
public enum ResponseCodeEnum {
	
	/**
	 * 操作成功
	 */
	C200(200,"操作成功"),
	/**
	 * 操作失败
	 */
	C500(500, "操作失败"),
	/**
	 * 业务处理异常
	 */
	C501(501, "业务处理异常"),
	/**
	 * 操作失败,token过期
	 */
	C1000(1000, "操作失败,token过期"),
	/**
	 * 操作失败，用户未登陆
	 */
	C1001(1001, "操作失败，用户未登陆");
	
	private Integer code;
	
	private String msg;
	
	private ResponseCodeEnum(Integer code, String msg) {
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
