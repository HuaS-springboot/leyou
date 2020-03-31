package com.vsj.consumer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vsj.common.constants.SysConfigConstants;
import com.vsj.common.enums.BountyModeEnum;
import com.vsj.common.model.Order;
import com.vsj.common.utils.StringUtil;
import com.vsj.consumer.service.IDistributionModeService;
import com.vsj.consumer.service.IOrderBountyService;
import com.vsj.consumer.service.IRangeModeService;
import com.vsj.dao.VsjSysConfigDAO;
import com.vsj.model.VsjSysConfig;

@Service
public class OrderBountyServiceImpl implements IOrderBountyService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IDistributionModeService distributionModeServiceImpl;
	
	@Autowired
	private IRangeModeService rangeModeServiceImpl;
	
	@Autowired
	private VsjSysConfigDAO vsjSysConfigDAO;
	
	@Override
	public void computingOrderBounty(Order order) {
		logger.debug("开始处理订单奖励信息,order = {}",order);
		long startTime = System.currentTimeMillis();
		if(null != order.getDividendOrder() && order.getDividendOrder() == 1){
			// 平台码
			Integer platformCode = order.getPlatformCode();
			// 查询该项目奖励模式
			Integer bountyMode = 0;
			VsjSysConfig vsjSysConfig = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.BOUNTY_MODE, platformCode);
			if(null != vsjSysConfig && StringUtil.isEmptyStr(vsjSysConfig.getValue())){
				bountyMode = Integer.parseInt(vsjSysConfig.getValue());
			}
			//根据不同模式走不同的业务
			if(null == bountyMode || BountyModeEnum.CLOSE.getCode().intValue() == bountyMode){
				logger.debug("项目未开通订单奖励");
				return ;
			}else if(BountyModeEnum.DISTRIBUTION.getCode().intValue() == bountyMode){
				logger.debug("开始计算分销模式奖励信息...");
				distributionModeServiceImpl.disposeOrderBounty(order);
			}else if(BountyModeEnum.RANGE.getCode().intValue() == bountyMode){
				logger.debug("开始计算经销商极差模式奖励信息...");
				rangeModeServiceImpl.disposeOrderBounty(order);
			}
		}else{
			logger.debug("该订单不是奖励订单,无需计算分红奖励");
		}
		
		logger.debug("订单奖励信息处理完成,耗时={}",(System.currentTimeMillis() - startTime));
	}
	
}
