package com.vsj.dao;

import com.vsj.model.VsjMemberProfit;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.MemberTeamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vsj.mapper.VsjMemberProfitMapper;

import java.util.List;

@Component
public class VsjMemberProfitDAO {
	@Autowired
	private VsjMemberProfitMapper vsjMemberProfitMapper;
	
	/**
	 * 
	 * @Title: updateWagesByPrimaryKey
	 * @Description: 更新提成信息
	 * @param memberId
	 *            memberId
	 * @param settledWages
	 *            已结算提成
	 * @param noSettledWages
	 *            未结算提成
	 * @param totalwages
	 *            总提成
	 * @return
	 * @author mario
	 * @return: int
	 */
	public int updateWagesByPrimaryKey(Integer memberId, double settledWages, double noSettledWages, double totalwages) {
		return vsjMemberProfitMapper.updateWagesByPrimaryKey(memberId, settledWages, noSettledWages, totalwages);
	}

	public VsjMemberProfit getMemberProfitByMemberId(Integer memberId,Integer platformCode) {
		return vsjMemberProfitMapper.getMemberProfitByMemberId(memberId,platformCode);
	}

	public int updateByMemberId(VsjMemberProfit vsjMemberProfit) {
		return vsjMemberProfitMapper.updateByMemberId(vsjMemberProfit);
	}

	public List<MemberTeamResponse> getMemberProfitList(BaseQueryStat baseQueryStat) {
		return vsjMemberProfitMapper.getMemberProfitList(baseQueryStat);
	}
}
