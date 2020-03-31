package com.vsj.common.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class AliVerificationResParam implements Serializable{

	private static final long serialVersionUID = -7468787330999014215L;
	
	/**
	 * 返回原因
	 */
	private String Message;
	
	/**
	 * 请求ID
	 */
	private String RequestId;
	
	/**
	 * 返回码,OK表示成功
	 */
	private String Code;
}
