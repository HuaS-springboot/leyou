package com.vsj.dao;

import com.vsj.model.request.BaseQueryStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vsj.mapper.VsjStageDistrMapper;
import com.vsj.model.VsjStageDistr;

@Component
public class VsjStageDistrDAO {
	@Autowired
	private VsjStageDistrMapper vsjStageDistrMapper;

	public VsjStageDistr selectByPlatformCode(Integer platformCode) {
		return vsjStageDistrMapper.selectByPlatformCode(platformCode);
	}

	public int insertStageDistr(VsjStageDistr vsjStageDistr) {
		return vsjStageDistrMapper.insertStageDistr(vsjStageDistr);
	}

	public VsjStageDistr getStageDistrByProductId(BaseQueryStat queryStat) {
		return vsjStageDistrMapper.getStageDistrByProductId(queryStat);
	}

	public int updateStageDistrById(VsjStageDistr vsjStageDistr) {
		return vsjStageDistrMapper.updateStageDistrById(vsjStageDistr);
	}

	public int deleteStageDistrById(BaseQueryStat queryStat) {
		return vsjStageDistrMapper.deleteStageDistrById(queryStat);
	}

	public void deleteStageDistrByProductId(BaseQueryStat baseQueryStat) {
		vsjStageDistrMapper.deleteStageDistrByProductId(baseQueryStat);
	}
}
