package com.vsj.common.utils;

import java.math.BigDecimal;

/**
 * 
 * @ClassName: NumberUtils
 * @Description: 数字工具类
 * @author: mario 
 * @date: 2019年7月27日 上午10:31:01
 * @copyright: 青岛微视角文化传媒有限公司
 */
public class NumberUtils {
	
	public static BigDecimal double2BigDecimal(double num){
		return new BigDecimal(Double.toString(num));
	}
}
