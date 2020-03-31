package com.vsj.consumer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vsj.common.constants.IncomeSourceConstants;
import com.vsj.common.constants.PaymentTypeConstants;
import com.vsj.common.enums.DistributionLevelEnum;
import com.vsj.common.enums.RewardModelEnum;
import com.vsj.common.model.Order;
import com.vsj.common.utils.NumberUtils;
import com.vsj.consumer.service.IDistributionModeService;
import com.vsj.dao.VsjMemberDAO;
import com.vsj.dao.VsjMemberProfitDAO;
import com.vsj.dao.VsjStageDistrDAO;
import com.vsj.dao.VsjOrderReceiptsRecordDAO;
import com.vsj.model.VsjMember;
import com.vsj.model.VsjOrderReceiptsRecord;
import com.vsj.model.VsjStageDistr;

@Service
public class DistributionModeServiceImpl implements IDistributionModeService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private VsjStageDistrDAO vsjStageDistrDAO;
	@Autowired
	private VsjMemberDAO vsjMemberDAO;
	@Autowired
	private VsjMemberProfitDAO vsjMemberProfitDAO;
	@Autowired
	private VsjOrderReceiptsRecordDAO vsjOrderReceiptsRecordDAO;

	@Override
	public void disposeOrderBounty(Order order) {
		VsjStageDistr vsjStageDistr = vsjStageDistrDAO.selectByPlatformCode(order.getPlatformCode());
		if (vsjStageDistr == null) {
			logger.debug("根据平台Code={},未查询到分销信息配置...", order.getPlatformCode());
			return;
		}
		int level = 0;
		if (null != vsjStageDistr.getLevel()) {
			level = vsjStageDistr.getLevel();
		} else {
			logger.debug("未开通或未配置分销等级...plateformCode={}", order.getPlatformCode());
			return;
		}
		Integer memberId = order.getMemberId();
		// 一级提成金额
		double firstLevelMoney = 0;
		// 二级提成金额
		double secondLevelMoney = 0;
		// 根据订单的会员Id查询会员信息
		VsjMember member = vsjMemberDAO.selectByPrimaryKey(memberId);
		if (null == member) {
			logger.info("根据memberId={}未查询到成员信息...", memberId);
			return;
		}
		// 会员推荐人Id
		Integer parentId = member.getParentId();
		if (null == parentId || parentId == 0) {
			logger.debug("会员memberId={},parentId为空,没有推荐人,无需计算分销等级奖励...", memberId);
			return;
		}
		// 计算1级分销提成
		if (level >= DistributionLevelEnum.ONE.getValue()) {
			logger.debug("开始计算订单id={}的一级分销提成", order.getOrderId());
			// 判断一级分销的比例和模式
			Integer firstLevelModel = vsjStageDistr.getFirstLevelModel();
			Integer firstLevelValue = vsjStageDistr.getFirstLevelValue();
			if (null == firstLevelModel || null == firstLevelValue) {
				logger.debug("未配置一级分销比例和奖励模式...");
			}
			// 固定金额
			if (firstLevelModel == RewardModelEnum.FIXED.getCode()) {
				firstLevelMoney = firstLevelValue.doubleValue();
			}
			// 百分比提成
			if (firstLevelModel == RewardModelEnum.PERCENTAGE.getCode()) {
				// 实付款*分成百分比例/100
				firstLevelMoney = order.getOrderAmountTotal().doubleValue() * firstLevelValue.intValue() / 100;
			}
			// 一级收益大于0,更新下单成员的推荐人的提成信息信息
			logger.debug("计算得出会员id={}应得一级分红={}", parentId, firstLevelMoney);
			if (firstLevelMoney > 0) {
				vsjMemberProfitDAO.updateWagesByPrimaryKey(parentId, 0, firstLevelMoney, firstLevelMoney);
				// 更新会员订单收益记录表
				vsjOrderReceiptsRecordDAO.insert(new VsjOrderReceiptsRecord(order.getOrderId(), parentId,
						NumberUtils.double2BigDecimal(firstLevelMoney), PaymentTypeConstants.INCOME,
						IncomeSourceConstants.DISTRIBUTION, order.getPlatformCode(),member.getLevelId()));
			}
		}
		// 计算二级分销提成
		if (level >= DistributionLevelEnum.TWO.getValue()) {
			// 查询一级上级的父节点
			VsjMember parentMember = vsjMemberDAO.selectByPrimaryKey(parentId);
			if (null == parentMember) {
				logger.info("根据memberId={}的推荐人Id={}未查询到成员信息...", memberId, parentId);
				return;
			}
			if (null == parentMember.getParentId() || parentMember.getParentId() == 0) {
				logger.debug("会员memberId={},parentId为空,没有推荐人,无需计算分销等级奖励...", parentId);
				return;
			}
			// 判断一级分销的比例和模式
			Integer secondLevelModel = vsjStageDistr.getSecondLevelModel();
			Integer secondLevelValue = vsjStageDistr.getSecondLevelValue();
			if (null == secondLevelModel || null == secondLevelValue) {
				logger.debug("未配置二级分销比例和奖励模式...");
			}
			// 固定金额
			if (secondLevelModel == RewardModelEnum.FIXED.getCode()) {
				secondLevelMoney = secondLevelValue.doubleValue();
			}
			// 百分比提成
			if (secondLevelModel == RewardModelEnum.PERCENTAGE.getCode()) {
				// 实付款*分成百分比例/100
				secondLevelMoney = order.getOrderAmountTotal().doubleValue() * secondLevelValue.intValue() / 100;
			}
			// 二级收益大于0,更新一级推荐人的推荐人的提成信息信息
			logger.debug("根据订单id={},计算出会员id={}应得二级分红={}", parentMember.getParentId(), secondLevelMoney);
			if (firstLevelMoney > 0) {
				vsjMemberProfitDAO.updateWagesByPrimaryKey(parentMember.getParentId(), 0, secondLevelMoney, secondLevelMoney);
				// 更新会员订单收益记录表
				vsjOrderReceiptsRecordDAO.insert(new VsjOrderReceiptsRecord(order.getOrderId(), parentId,
						NumberUtils.double2BigDecimal(secondLevelMoney), PaymentTypeConstants.INCOME,
						IncomeSourceConstants.DISTRIBUTION, order.getPlatformCode(),member.getLevelId()));
			}
		}
	}
	
}
