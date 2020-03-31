package com.vsj.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vsj.common.utils.StringUtil;

/**
 * 
 * @ClassName: BaseController
 * @Description: 基础通用Controller
 * 用于获取平台码等通用信息
 * @author: mario 
 * @date: 2019年7月25日 下午5:50:24
 * @copyright: 青岛微视角文化传媒有限公司
 */
public class BaseController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;

	/**
	 * 平台码
	 * @return
	 */
	public Integer getPlatformCode(){
		String platformCode = request.getHeader("platformCode");
		return Integer.parseInt(platformCode);
	}

	/**
	 * 后台登录用户的用户id
	 * @return
	 */
	public Integer getUserId(){
		String userId = request.getHeader("userId");
		return Integer.parseInt(userId);
	}

	/**
	 * 手机端登录用户的用户id
	 * @return
	 */
	public Integer getRegisterId(){
		String registerId = request.getHeader("registerId");
		return Integer.parseInt(registerId);
	}
	
	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
}
