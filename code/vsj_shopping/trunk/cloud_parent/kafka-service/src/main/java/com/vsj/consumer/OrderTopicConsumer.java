package com.vsj.consumer;

import com.alibaba.fastjson.JSON;
import com.vsj.common.model.Order;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.service.IKafkaTopicConsumer;
import com.vsj.common.service.MemberService;
import com.vsj.consumer.service.IOrderBountyService;
import com.vsj.common.service.VsjMemberUpgradeRulesService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderTopicConsumer implements IKafkaTopicConsumer {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IOrderBountyService orderBountyServiceImpl;
	@Autowired
	private VsjMemberUpgradeRulesService vsjMemberUpgradeRulesService;
	@Autowired
	private MemberService memberService;
	
	@Override
	public void doConsume(String record, boolean isTask) throws Exception {
		long startTime = System.currentTimeMillis();
		//实体转换
		Order order = JSON.parseObject(record, Order.class);
		logger.debug("kafka接收到的待消费订单记录,开始处理...order={}",order);
		//会员上下级关系处理
		QueryStat queryStat = new QueryStat();
		// 上级邀请人id
		queryStat.setReferrerId(order.getReferrerId());
		// 当前人id
		queryStat.setRegId(order.getRegId());
		// 接入类型 首次下单
		queryStat.setType(2);
		queryStat.setPlatformCode(order.getPlatformCode());
		memberService.disposeRelation(queryStat);
		//处理订单奖励（返利）信息
		orderBountyServiceImpl.computingOrderBounty(order);
		//处理处理统计信息,判断会员升级条件
		vsjMemberUpgradeRulesService.disposeMemberLevel(order);
		
		logger.debug("kafka订单信息处理完成,耗时={}",(System.currentTimeMillis() - startTime));
	}

}
