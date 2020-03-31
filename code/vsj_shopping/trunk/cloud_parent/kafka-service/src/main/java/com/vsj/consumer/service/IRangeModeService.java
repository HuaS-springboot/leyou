package com.vsj.consumer.service;

import com.vsj.common.model.Order;

/**
 * 
 * @ClassName: IRangeModeService
 * @Description: 经销商极差模式处理
 * @author: mario 
 * @date: 2019年7月25日 下午2:04:02
 * @copyright: 青岛微视角文化传媒有限公司
 */
public interface IRangeModeService {
	void disposeOrderBounty(Order order);
}
