package com.vsj.common.utils;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 
 * @ClassName: StringUtil
 * @Description: 字符串工具类
 * @author: mario
 * @date: 2019年7月20日 下午9:02:46
 * @copyright: 青岛微视角文化传媒有限公司
 */
public class StringUtil {

	/**
	 * 
	 * @Title: isEmptyStr
	 * @Description: 字符串是否为空
	 * @param str
	 * @return
	 * @author mario
	 * @return: boolean
	 */
	public static boolean isEmptyStr(String str) {
		if (str != null) {
			str = str.trim();
		}
		return StringUtils.isEmpty(str) || "undefined".equals(str) || "null".equals(str);
	}

	/**
	 * 
	 * @Title: isNoEmptyStr
	 * @Description: 字符串是否非空
	 * @param str
	 * @return
	 * @author mario
	 * @return: boolean
	 */
	public static boolean isNoEmptyStr(String str) {
		return !isEmptyStr(str);
	}

	/**
	 * 
	 * @Title: getUUID
	 * @Description: 生成UUID,去掉"-"
	 * @return
	 * @author mario
	 * @return: String
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		// 去掉"-"符号
		return uuid.replaceAll("-", "");
	}

	/**
	 * 
	 * @Title: isEmptyList
	 * @Description: 判断数组是否为空
	 * @param list
	 * @return
	 * @author mario
	 * @return: boolean
	 */
	public static <T> boolean isEmptyList(List<T> list) {
		if (list == null || list.size() == 0) {
			return true;
		}

		if (list.size() == 1 && list.get(0) == null) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @Title: getVerificationCode
	 * @Description: 获取4位验证码
	 * @return
	 * @author mario
	 * @return: String
	 */
	public static String getVerificationCode(){
		StringBuffer code = new StringBuffer();
		code.append(RandomUtils.nextInt(0,10));
		code.append(RandomUtils.nextInt(0,10));
		code.append(RandomUtils.nextInt(0,10));
		code.append(RandomUtils.nextInt(0,10));
		return code.toString();
	}
	
	/**
	 * 
	 * @Title: isEmptyMap
	 * @Description: 判断map是否为空
	 * @param map
	 * @return
	 * @author mario
	 * @return: boolean
	 */
	public static <T,E> boolean isEmptyMap(Map<T,E> map){
		if(map == null || map.isEmpty()){
			return true;
		}
		return false;
	}
}
