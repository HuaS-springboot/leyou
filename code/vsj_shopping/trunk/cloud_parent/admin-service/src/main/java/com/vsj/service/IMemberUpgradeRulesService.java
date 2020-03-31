package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.MemberUpgrade;

import java.util.List;

public interface IMemberUpgradeRulesService {

    BaseResponseParam findMemberUpgradeRules(Integer levelId,Integer platformCode);
    BaseResponseParam editUpgradeRules(List<MemberUpgrade> list , Integer platformCode);
    BaseResponseParam delByPrimaryKey(Integer id,Integer platformCode);

}
