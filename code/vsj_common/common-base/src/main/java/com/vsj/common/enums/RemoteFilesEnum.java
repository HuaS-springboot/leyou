package com.vsj.common.enums;

/**
 * 
 * @ClassName: RemoteFilesEnum
 * @Description: 远程文件存储配置枚举
 * @author: mario 
 * @date: 2019年7月30日 下午5:06:28
 * @copyright: 青岛微视角文化传媒有限公司
 */
public enum RemoteFilesEnum {
	
	/**
	 * 0,关闭,未配置
	 */
	CLOSE(0,"关闭"),
	/**
	 * 1,阿里云OSS
	 */
	ALI_OSS(1,"阿里云OSS"),
	/**
	 * 2,七牛云
	 */
	QINIU_CLOUD(2,"七牛云"),
	/**
	 * 3,FTP
	 */
	FTP(3,"FTP"),
	/**
	 * 4,腾讯云
	 */
	TENCENT_CLOUD(4,"腾讯云");
	
	private Integer code;
	
	private String msg;
	
	private RemoteFilesEnum(Integer code, String msg) {
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
