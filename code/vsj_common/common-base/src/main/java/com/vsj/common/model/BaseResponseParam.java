package com.vsj.common.model;

import java.io.Serializable;

import com.vsj.common.enums.ResponseCodeEnum;

/**
 * 
 * @ClassName: BaseResponseParam
 * @Description: 通用返回数据对象
 * @author: mario 
 * @date: 2019年7月20日 下午8:56:14
 * @copyright: 青岛微视角文化传媒有限公司
 * @param <T>
 */
public class BaseResponseParam<T> extends BaseToString implements Serializable{

	private static final long serialVersionUID = 3316850245892997310L;

	private Integer code;
	
	private String message;

	private T data;
	
	public BaseResponseParam(){
		
	}

	public BaseResponseParam(Integer code ,String message){
		this.code = code;
		this.message = message;
	}
	
	public BaseResponseParam(Integer code ,String message,T data){
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public static <T> BaseResponseParam<T> success(){
		return new BaseResponseParam<T>(ResponseCodeEnum.C200.getCode(),ResponseCodeEnum.C200.getMsg());
	}
	
	public static <T> BaseResponseParam<T> success(String msg){
		return new BaseResponseParam<T>(ResponseCodeEnum.C200.getCode(),msg);
	}

	public static <T> BaseResponseParam<T> success(T data){
		return new BaseResponseParam<T>(ResponseCodeEnum.C200.getCode(),ResponseCodeEnum.C200.getMsg(),data);
	}
	
	public static <T> BaseResponseParam<T> fail(){
		return new BaseResponseParam<T>(ResponseCodeEnum.C500.getCode(),ResponseCodeEnum.C500.getMsg());
	}
	
	public static <T> BaseResponseParam<T> fail(String message){
		return new BaseResponseParam<T>(ResponseCodeEnum.C500.getCode(),message);
	}
	
	public static <T> BaseResponseParam<T> fail(T data){
		return new BaseResponseParam<T>(ResponseCodeEnum.C500.getCode(),ResponseCodeEnum.C500.getMsg(),data);
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
}
