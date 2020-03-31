package com.vsj.common.model;

import java.io.Serializable;

import lombok.Data;
/**
 * 
 * @ClassName: KdniaoTraces
 * @Description: 快递鸟物流轨迹
 * @author: mario 
 * @date: 2019年7月31日 下午2:31:57
 * @copyright: 青岛微视角文化传媒有限公司
 */
@Data
public class KdniaoTraces implements Serializable{

	private static final long serialVersionUID = 5935697724830117079L;
	/**
	 * 物流信息
	 */
	private String AcceptStation;
	/**
	 * 时间
	 */
	private String AcceptTime;
}
