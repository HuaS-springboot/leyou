package com.vsj.common.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
/**
 * 
 * @ClassName: KdniaoResponseParam
 * @Description: 快递鸟查询请求返回参数
 * @author: mario 
 * @date: 2019年7月31日 下午2:31:42
 * @copyright: 青岛微视角文化传媒有限公司
 */
@Data
public class KdniaoResponseParam implements Serializable{

	private static final long serialVersionUID = 3576268606908918592L;
	/**
	 * 物流单号
	 */
	private String LogisticCode;
	/**
	 * 物流公司编码
	 */
	private String ShipperCode;
	/**
	 * 物流轨迹信息
	 */
	private List<KdniaoTraces> Traces;
	/**
	 * 状态
	 */
	private String State;
	/**
	 * 商户Id
	 */
	private String EBusinessID;
	/**
	 * 原因说明
	 */
	private String Reason;
	/**
	 * 查询是否成功
	 */
	private boolean Success;
	
}
