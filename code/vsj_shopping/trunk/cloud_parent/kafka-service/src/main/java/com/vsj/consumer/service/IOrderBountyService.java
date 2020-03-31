package com.vsj.consumer.service;

import com.vsj.common.model.Order;

/**
 * 
 * @ClassName: IOrderBountyService
 * @Description: 订单奖励金处理
 * @author: mario 
 * @date: 2019年7月25日 下午1:36:47
 * @copyright: 青岛微视角文化传媒有限公司
 */
public interface IOrderBountyService {
	void computingOrderBounty(Order order);
}
