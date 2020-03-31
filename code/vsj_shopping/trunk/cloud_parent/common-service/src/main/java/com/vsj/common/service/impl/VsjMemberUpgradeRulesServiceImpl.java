package com.vsj.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.vsj.common.model.Order;
import com.vsj.common.model.request.*;
import com.vsj.common.service.VsjMemberUpgradeRulesService;
import com.vsj.dao.*;
import com.vsj.model.VsjMember;
import com.vsj.model.VsjMemberLevel;
import com.vsj.model.VsjMemberProfit;
import com.vsj.model.VsjMemberUpgradeRules;

import com.vsj.model.request.BaseQueryStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname VsjMemberUpgradeRulesServiceImpl
 * @Description 会员数升级业务实现
 * @Date 2019/8/5 15:27
 * @Created by wangzx
 */
@Service
public class VsjMemberUpgradeRulesServiceImpl implements VsjMemberUpgradeRulesService {

    @Autowired
    private VsjMemberUpgradeRulesDAO vsjMemberUpgradeRulesDAO;
    @Autowired
    private VsjMemberDAO vsjMemberDAO;
    @Autowired
    private MemberLevelDao memberLevelDao;
    @Autowired
    private VsjOrderDAO vsjOrderDAO;
    @Autowired
    private VsjMemberProfitDAO vsjMemberProfitDAO;


    @Override
    public void disposeMemberLevel(Order order) {
        // 查询会员当前等级
        VsjMember member = vsjMemberDAO.selectByRegId(order.getRegId());
        // 查询会员下一等级id
        VsjMemberLevel memberLevel = memberLevelDao.getLevelBySort(member.getLevelId() + 1, order.getPlatformCode());

        // 查询第一条规则
        VsjMemberUpgradeRules firstRule = vsjMemberUpgradeRulesDAO.getRulesByParentId(null, order.getPlatformCode(),
                memberLevel.getId());
        RulesResult firstRuleResult = JSON.parseObject(firstRule.getData(), RulesResult.class);
        // 校验第一条规则是否成立
        boolean firstBool = firstBool(order, firstRule, firstRuleResult);


        // 查询第二条规则
        VsjMemberUpgradeRules secondRule = vsjMemberUpgradeRulesDAO.getRulesByParentId(firstRule.getId(), order.getPlatformCode(),
                memberLevel.getId());
        RulesResult secondRuleRuleResult = JSON.parseObject(secondRule.getData(), RulesResult.class);
        // 校验第二条规则是否成立
        boolean secondBool = secondBool(order, secondRule, secondRuleRuleResult);

        // 查询第三条数据
        VsjMemberUpgradeRules threeRule = vsjMemberUpgradeRulesDAO.getRulesByParentId(secondRule.getId(), order.getPlatformCode(),
                memberLevel.getId());
        RulesResult threeRuleRuleResult = JSON.parseObject(secondRule.getData(), RulesResult.class);
        // 校验第二条规则是否成立
        boolean threeBool = threeBool(order, threeRule, threeRuleRuleResult);

        // 校验总结果
        boolean finalBol = getFinalBool(firstBool, secondBool, threeBool, secondRule, threeRule);

        // 会员升级
        if (finalBol) {
            member.setLevelId(memberLevel.getId());
            vsjMemberDAO.updateVsjMember(member);
        }
    }

    /**
     * 校验总结果
     *
     * @param firstBool
     * @param secondBool
     * @param threeBool
     * @param secondRule
     * @param threeRule
     * @return
     */
    private boolean getFinalBool(boolean firstBool, boolean secondBool, boolean threeBool, VsjMemberUpgradeRules secondRule,
                                 VsjMemberUpgradeRules threeRule) {
        // 保存第二级与第一级的结果
        boolean firstSecondBol = false;

        // 保存最终结果
        boolean finalBol = false;

        // 第二级与第一级的最终关系
        if (secondRule != null) {
            Byte relation = secondRule.getParentRelation();
            if (relation == 0) {
                // 第二级与第一级是或的关系
                if (firstBool || secondBool) {
                    firstSecondBol = true;
                }
            } else {
                // 第二级与第一级是与的关系
                if (firstBool && secondBool) {
                    firstSecondBol = true;
                }
            }
        } else {
            // 如果没有第二级,则第一级的结果就是最终结果
            finalBol = firstBool;
        }

        if (secondRule != null && threeRule != null) {
            Byte relation = threeRule.getParentRelation();
            if (relation == 0) {
                // 第三级与第二级是或的关系
                if (threeBool || firstSecondBol) {
                    finalBol = true;
                }
            } else {
                // 第二级与第一级是与的关系
                if (threeBool && firstSecondBol) {
                    finalBol = true;
                }
            }
        } else if (secondRule != null && threeRule == null) {
            // 如果有第二规则没有第三规则，最终结果为一二条规则结果
            finalBol = firstSecondBol;
        } else if (secondRule == null && threeRule == null) {
            // 如果没有第二三条规则,总结果为第一条数据的结果
            finalBol = firstBool;
        }
        return finalBol;
    }

    /**
     * 第一条规则验证
     *
     * @param firstRule
     * @param firstRuleResult
     * @return
     */
    public boolean firstBool(Order order, VsjMemberUpgradeRules firstRule, RulesResult firstRuleResult) {
        if (firstRule == null) {
            return false;
        }
        return isResultBool(order, firstRule.getType(), firstRule.getCurrentRelation(), firstRuleResult);
    }

    /**
     * 第二条规则验证
     *
     * @param secondRule
     * @param secondRuleRuleResult
     * @return
     */
    public boolean secondBool(Order order, VsjMemberUpgradeRules secondRule, RulesResult secondRuleRuleResult) {
        if (secondRule == null) {
            return false;
        }
        return isResultBool(order, secondRule.getType(), secondRule.getCurrentRelation(), secondRuleRuleResult);
    }

    /**
     * 第三条规则验证
     *
     * @param threeRule
     * @param threeRuleRuleResult
     * @return
     */
    public boolean threeBool(Order order, VsjMemberUpgradeRules threeRule, RulesResult threeRuleRuleResult) {
        if (threeRule == null) {
            return false;
        }
        return isResultBool(order, threeRule.getType(), threeRule.getCurrentRelation(), threeRuleRuleResult);
    }

    /**
     * @Description 判断当前条件是否成立
     * @Author wangzx
     * @Date 2019/8/5 17:04
     * @Param [type, relation, ruleResult]
     * type:0=个人订单；1=一级订单
     * relation：当前数据与/或关系
     * ruleResult：系统配置数据
     * @Return boolean
     * @Exception
     */
    private boolean isResultBool(Order order, Byte type, Byte relation, RulesResult ruleResult) {

        boolean mBol = false;

        // 总消费满多少元
        RulesMoney rulesMoney = ruleResult.getRulesMoney();
        if (rulesMoney != null && rulesMoney.getStatus() == 1) {
            // 查询该会员总消费金额
            BaseQueryStat queryStat = new BaseQueryStat();
            // 当前会员id
            queryStat.setRegId(order.getRegId());
            // 平台code码
            queryStat.setPlatformCode(order.getPlatformCode());
            // 0=个人订单；1=一级订单
            queryStat.setType(type.intValue());
            Double memberConMoney = vsjOrderDAO.getMemberConMoney(queryStat);
            if (memberConMoney >= rulesMoney.getTotalMoney()) {
                mBol = true;
            }
        }

        boolean pBol = false;
        // 购买xxx商品满xxx笔
        RulesProductCount rulesProductCount = ruleResult.getRulesProductCount();
        if (rulesProductCount != null && rulesProductCount.getStatus() == 1) {
            BaseQueryStat queryStat = new BaseQueryStat();
            // 设置购买的产品id
            queryStat.setIds(rulesProductCount.getProductId());
            // 当前人id
            queryStat.setId(order.getRegId());
            int pcount = vsjOrderDAO.getProCount(queryStat);
            if (pcount >= rulesProductCount.getCount()) {
                pBol = true;
            }
        }

        boolean oBol = false;
        // 总消费满多少笔
        RulesOrderCount rulesOrderCount = ruleResult.getRulesOrderCount();
        if (rulesOrderCount != null && rulesOrderCount.getStatus() == 1) {
            QueryStat queryStat = new QueryStat();
            VsjMemberProfit profit = vsjMemberProfitDAO.getMemberProfitByMemberId(order.getMemberId(),order.getPlatformCode());
            if (profit.getCompletOrder() >= rulesOrderCount.getOrderCount()) {
                oBol = false;
            }
        }

        boolean dBol = false;
        // 结算分红奖励金满xxx元
        RulesDividend rulesDividend = ruleResult.getRulesDividend();
        if (rulesDividend != null && rulesDividend.getStatus() == 1) {
            VsjMemberProfit profit = vsjMemberProfitDAO.getMemberProfitByMemberId(order.getMemberId(),order.getPlatformCode());
            if (profit.getSettledWages().doubleValue() >= rulesDividend.getDividendMoney()) {
                dBol = true;
            }
        }
        return relation == 0 ? (mBol || pBol || oBol || dBol) : (mBol && pBol && oBol && dBol);
    }
}
