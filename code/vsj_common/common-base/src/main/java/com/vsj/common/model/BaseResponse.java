package com.vsj.common.model;

import java.io.Serializable;
import java.util.List;
import com.vsj.common.enums.ResponseCodeEnum;

/**
 * 
 * @ClassName: BaseResponse
 * @Description: 列表请求通用返回参数
 * @author: mario 
 * @date: 2019年7月20日 下午8:56:29
 * @copyright: 青岛微视角文化传媒有限公司
 * @param <T>
 */
public class BaseResponse<T> extends BaseToString implements Serializable{
	
	private static final long serialVersionUID = -1159557819118837017L;

	private Integer code;
	
	private String message;
	
	private List<BaseResponseParam<T>> dataList;
	
	public BaseResponse(){
		
	}
	
	public BaseResponse(Integer code ,String message){
		this.code = code;
		this.message = message;
	}
	
	public BaseResponse(Integer code ,String message,List<BaseResponseParam<T>> dataList){
		this.code = code;
		this.message = message;
		this.dataList = dataList;
	}
	
	public static <T> BaseResponse<T> success(){
		return new BaseResponse<T>(ResponseCodeEnum.C200.getCode(),ResponseCodeEnum.C200.getMsg());
	}
	
	public static <T> BaseResponse<T> success(List<BaseResponseParam<T>> dataList){
		return new BaseResponse<T>(ResponseCodeEnum.C200.getCode(),ResponseCodeEnum.C200.getMsg(),dataList);
	}
	
	public static <T> BaseResponse<T> fail(){
		return new BaseResponse<T>(ResponseCodeEnum.C500.getCode(),ResponseCodeEnum.C500.getMsg());
	}

	public static <T> BaseResponse<T> fail(String msg){
		return new BaseResponse<T>(ResponseCodeEnum.C500.getCode(),msg);
	}
	
	public static <T> BaseResponse<T> error(){
		return new BaseResponse<T>(ResponseCodeEnum.C501.getCode(),ResponseCodeEnum.C501.getMsg());
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

	public List<BaseResponseParam<T>> getDataList() {
		return dataList;
	}

	public void setDataList(List<BaseResponseParam<T>> dataList) {
		this.dataList = dataList;
	}

}
