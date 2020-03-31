package com.vsj.model;

import java.io.Serializable;

import lombok.Data;

/**
 * @ClassName: VsjStageDistrModel
 * @Description: 分销模式奖励表
 * @author: mario 
 * @date: 2019年7月25日 下午6:27:43
 * @copyright: 青岛微视角文化传媒有限公司
 */
@Data
public class VsjStageDistr implements Serializable{
	
	private static final long serialVersionUID = 323163085184116773L;
	
	private Integer id;
	
	private Integer level;
	
	private Integer firstLevelModel;
	
	private Integer firstLevelValue;
	
	private Integer secondLevelModel;
	
	private Integer secondLevelValue;
	
	private Integer platformCode;

	private Integer productId;
}
