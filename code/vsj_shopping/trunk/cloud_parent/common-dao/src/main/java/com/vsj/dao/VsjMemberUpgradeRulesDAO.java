package com.vsj.dao;

import com.vsj.mapper.VsjMemberUpgradeRulesMapper;
import com.vsj.model.VsjMemberUpgradeRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname VsjMemberUpgradeRulesDAO
 * @Description TODO
 * @Date 2019/8/5 15:29
 * @Created by wangzx
 */
@Component
public class VsjMemberUpgradeRulesDAO {

    @Autowired
    private VsjMemberUpgradeRulesMapper vsjMemberUpgradeRulesMapper;

    public VsjMemberUpgradeRules getRulesByParentId(Integer parentId, Integer platformCode,Integer levelId) {
        return vsjMemberUpgradeRulesMapper.getRulesByParentId(parentId,platformCode,levelId);
    }

    public List<VsjMemberUpgradeRules> findMemberUpgradeRules(Integer levelId,Integer platformCode){
        return vsjMemberUpgradeRulesMapper.findMemberUpgradeRules(levelId,platformCode);
    }

    public int insert(VsjMemberUpgradeRules vsjMemberUpgradeRules){
        return vsjMemberUpgradeRulesMapper.insert(vsjMemberUpgradeRules);
    }

    public int updateByPrimaryKey(VsjMemberUpgradeRules vsjMemberUpgradeRules){
        return vsjMemberUpgradeRulesMapper.updateByPrimaryKey(vsjMemberUpgradeRules);
    }

    public int delByPrimaryKey(Integer id,Integer platformCode){
        return vsjMemberUpgradeRulesMapper.delByPrimaryKey(id,platformCode);
    }
}
