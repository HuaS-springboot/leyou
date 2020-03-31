package com.vsj.service.impl;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.BaseResponseParam;

import com.vsj.common.model.request.MemberUpgrade;
import com.vsj.dao.VsjMemberUpgradeRulesDAO;
import com.vsj.model.VsjMemberUpgradeRules;
import com.vsj.service.IMemberUpgradeRulesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberUpgradeRulesServiceImpl implements IMemberUpgradeRulesService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private VsjMemberUpgradeRulesDAO vsjMemberUpgradeRulesDAO;

    @Autowired
    private AbstractObjectConverter<MemberUpgrade, VsjMemberUpgradeRules> convert;

    @Override
    public BaseResponseParam findMemberUpgradeRules(Integer levelId,Integer platformCode) {
        return BaseResponseParam.success(vsjMemberUpgradeRulesDAO.findMemberUpgradeRules(levelId,platformCode));
    }

    @Override
    public BaseResponseParam editUpgradeRules(List<MemberUpgrade> list, Integer platformCode) {
        //用于新增时存储parentId
        int tempId = 0;
        for( MemberUpgrade memberUpgrade : list ){
            VsjMemberUpgradeRules vsjMemberUpgradeRules = convert.convert(memberUpgrade,VsjMemberUpgradeRules.class);
            if(null == vsjMemberUpgradeRules){
                logger.debug("转换失败...");
                return BaseResponseParam.fail("保存失败...");
            }
            //设置上级Id
            vsjMemberUpgradeRules.setParentId(tempId);
            //设置平台码
            vsjMemberUpgradeRules.setPlatformCode(platformCode);
            //判定新增或保存
            int i = -1;
            if(null == vsjMemberUpgradeRules.getId()){
                //新增
                i = vsjMemberUpgradeRulesDAO.insert(vsjMemberUpgradeRules);
            }else{
                //修改
                i = vsjMemberUpgradeRulesDAO.updateByPrimaryKey(vsjMemberUpgradeRules);
            }
            if(i > 0){
                tempId = vsjMemberUpgradeRules.getId();
            }else{
                return BaseResponseParam.fail("修改失败...");
            }
        }
        return BaseResponseParam.success();
    }

    @Override
    public BaseResponseParam delByPrimaryKey(Integer id, Integer platformCode) {
        return vsjMemberUpgradeRulesDAO.delByPrimaryKey(id,platformCode) > 0?BaseResponseParam.success() :BaseResponseParam.fail();
    }
}
