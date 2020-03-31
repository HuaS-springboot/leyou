package com.vsj.consumer.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.vsj.common.constants.IncomeSourceConstants;
import com.vsj.common.constants.PaymentTypeConstants;
import com.vsj.common.model.Order;
import com.vsj.common.utils.NumberUtils;
import com.vsj.consumer.service.IRangeModeService;
import com.vsj.dao.StageSchemaDAO;
import com.vsj.dao.VsjMemberDAO;
import com.vsj.dao.VsjMemberProfitDAO;
import com.vsj.dao.VsjOrderReceiptsRecordDAO;
import com.vsj.model.VsjMember;
import com.vsj.model.VsjOrderReceiptsRecord;
import com.vsj.model.response.StageSchemaLevelInfo;

/**
 * 
 * @ClassName: RangeModeServiceImpl
 * @Description: 经销商极差模式奖励处理
 * @author: mario 
 * @date: 2019年8月2日 下午2:27:04
 * @copyright: 青岛微视角文化传媒有限公司
 */
@Service
public class RangeModeServiceImpl implements IRangeModeService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private VsjMemberDAO vsjMemberDAO;

	@Autowired
	private StageSchemaDAO stageSchemaDAO;
	
	@Autowired
	private VsjMemberProfitDAO vsjMemberProfitDAO;
	@Autowired
	private VsjOrderReceiptsRecordDAO vsjOrderReceiptsRecordDAO;

	{
		/**
		 * 经销商极差模式奖励说明： 假定会员等级为：VIP①--店主②--代理商③ 商品P的推荐奖励为：①5元②10元③30元
		 * 平级奖励为：①1元②2元③5元 推荐关系为A--->B--->C--->D--->E--->F--->G--->H
		 * a)G为①,BDEF为②,AC为③ 若H购买商品P 1)不开启平级奖励： G获得5元,F获得10-5=5元,C获得30-10=20元
		 * 2)开启1级平级奖励 G获得5元,F获得10-5=5元,E获得平级奖励2元,C获得30-10=20元,A获得平级奖励5元
		 * 3)开启2级平级奖励
		 * G获得5元,F获得10-5=5元,E获得平级奖励2元,D获得平级奖励2元,C获得30-10=20元,A获得平级奖励5元
		 * b)G为①,F为③,DE为② 若H购买商品P 不开启平级奖励： G获得5元,F获得30-5=25元,其他人不得钱
		 * 
		 */
	}

	@Override
	public void disposeOrderBounty(Order order) {
		int memberId = order.getMemberId();
		// 开始计算奖励信息的用户起始点
		VsjMember member = null;
		// 判断是否为自购订单
		if (null == order.getReferrerId() || (order.getMemberId().intValue() == order.getReferrerId().intValue())) {
			logger.debug("该订单为自购订单,开始计算自购用户奖励信息");
			member = vsjMemberDAO.selectByPrimaryKey(order.getMemberId());
		} else {
			// 查询下单会员信息
			VsjMember orderMember = vsjMemberDAO.selectByPrimaryKey(memberId);
			if (null == orderMember.getParentId() || orderMember.getParentId() == 0) {
				logger.debug("会员id={}无推荐人,不需要计算返利信息...");
				return;
			}
			// 查询父节点会员信息及等级,开始计算奖励信息
			member = vsjMemberDAO.selectByPrimaryKey(orderMember.getParentId());
		}
		logger.debug("开始计算奖励的起始点用户信息={}", JSON.toJSONString(member));
		// 查询奖励配置
		List<StageSchemaLevelInfo> stageSchemaLevelInfoList = null;
		if (order.getIsCommission().byteValue() == 0) {
			stageSchemaLevelInfoList = stageSchemaDAO.selectHighLevelAndDefaultSchema(member.getLevelId(),
					order.getPlatformCode());
		} else {
			stageSchemaLevelInfoList = stageSchemaDAO.selectHighLevelAndSchema(member.getLevelId(),
					order.getProductId(), order.getPlatformCode());
		}
		// 奖励配置List转map
		Map<Integer, StageSchemaLevelInfo> levelInfoMap = stageSchemaLevelInfoList.stream().collect(
				Collectors.toMap(StageSchemaLevelInfo::getLevelId, stageSchemaLevelInfo -> stageSchemaLevelInfo));
		// 开始处理奖励信息
		orderBounty(levelInfoMap, member, order, levelInfoMap.get(member.getLevelId()).getSort());
	}

	/**
	 * 
	 * @Title: orderBounty
	 * @Description: 递归计算订单产生的用户奖励信息
	 * @param levelInfoMap
	 *            等级信息
	 * @param member
	 *            当前正在计算奖励的人员信息
	 * @param order
	 *            订单信息
	 * @param sort
	 *            当前处理到了这个权重 如果人员等级对应权重比这个低则考虑平级 比这个高则考虑是跳级 相等则考虑对应等级分红
	 * @author mario
	 * @return: boolean
	 */
	private boolean orderBounty(Map<Integer, StageSchemaLevelInfo> levelInfoMap, VsjMember member, Order order, int sort) {
		logger.debug("开始处理用户member={}的分成信息,当前处理等级权重sort={}", JSON.toJSONString(member), sort);
		if (levelInfoMap.isEmpty()) {
			// 表示所有分红信息都处理完了
			logger.debug("极差模式奖励及平级奖励信息已处理完毕...结束流程...");
			return true;
		}
		int levelId = member.getLevelId();
		StageSchemaLevelInfo levelInfo = levelInfoMap.get(levelId);
		// 奖励金额
		double bounTyMoney = 0;
		if (null == levelInfo) {
			logger.debug("当前级别会员分红已经处理完成...无需处理");
		} else {
			//对比当前处理权重和用户等级权重
			if(levelInfo.getSort().intValue() == sort){
				//权重相等,计算对应等级的奖励
				if(levelInfo.getBonusUnits() == 0){
					//百分比
					bounTyMoney = order.getOrderAmountTotal().doubleValue() * levelInfo.getBonusNum().doubleValue() * 0.01;
				}else if(levelInfo.getBonusUnits() == 1){
					//元
					bounTyMoney = levelInfo.getBonusNum().doubleValue();
				}
				//当前等级处理完成,权重+1
				sort ++;
			}else if(levelInfo.getSort().intValue() > sort){
				//用户等级权重大于当前处理等级的权重,跳级,计算对应等级的奖励
				for(StageSchemaLevelInfo info : levelInfoMap.values()){
					List<Integer> removeLevelList = new ArrayList<>();
					//比用户等级权重还小的移除去
					if(info.getSort().intValue() < levelInfo.getSort().intValue()){
						removeLevelList.add(info.getLevelId());
					}
					for(Integer i : removeLevelList){
						levelInfoMap.remove(i);
					}
				}
				//计算会员对应等级的奖励
				if(levelInfo.getBonusUnits() == 0){
					//百分比
					bounTyMoney = order.getOrderAmountTotal().doubleValue() * levelInfo.getBonusNum().doubleValue() * 0.01;
				}else if(levelInfo.getBonusUnits() == 1){
					//元
					bounTyMoney = levelInfo.getBonusNum().doubleValue();
				}
				//当前等级处理完成,权重=当前处理等级的权重+1
				sort = levelInfo.getSort() + 1;
			}else if(levelInfo.getSort().intValue() < sort){
				//用户等级权重小于当前处理等级的权重,计算平级奖励
				if(levelInfo.getOpenPeers()==0 || levelInfo.getPeersHierarchy() < 1){
					//如果平级奖励是关闭的,或者平级奖励层级剩余不足1个
					//则把该等级的奖励信息从map中移除
					levelInfoMap.remove(levelId);
				}else{
					//计算平级奖励
					//处理一次就把平级层级-1
					int peersHierarchy = levelInfo.getPeersHierarchy()-1;
					if(peersHierarchy == 0){
						//把该等级的奖励信息从map中移除
						levelInfoMap.remove(levelId);
					}else{
						levelInfo.setPeersHierarchy(peersHierarchy);
					}
					
				}
				//权重不变
				//sort = sort
			}
		}
		// 奖励金额有变动
		if(bounTyMoney != 0){
			//更新会员收益
			vsjMemberProfitDAO.updateWagesByPrimaryKey(member.getId(), 0, bounTyMoney, bounTyMoney);
			//会员订单收益记录表
			vsjOrderReceiptsRecordDAO.insert(new VsjOrderReceiptsRecord(order.getOrderId(), member.getId(),
					NumberUtils.double2BigDecimal(bounTyMoney), PaymentTypeConstants.INCOME,
					IncomeSourceConstants.RANGE, order.getPlatformCode(),member.getLevelId()));
		}
		if (null == member.getParentId() || member.getParentId() == 0) {
			// 表示没有上级了,到头了
			logger.debug("当前用户已达顶级,没有上级.极差模式奖励处理完成...");
			return true;
		}
		// 继续往上一级别找
		VsjMember parentMember = vsjMemberDAO.selectByPrimaryKey(member.getParentId());
		return orderBounty(levelInfoMap, parentMember, order, sort);
	}
	
}
