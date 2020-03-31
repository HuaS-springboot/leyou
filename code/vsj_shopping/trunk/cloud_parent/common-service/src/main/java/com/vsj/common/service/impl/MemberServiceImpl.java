package com.vsj.common.service.impl;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.service.MemberService;
import com.vsj.common.utils.StringUtil;
import com.vsj.dao.*;
import com.vsj.model.VsjMember;
import com.vsj.model.VsjMemberProfit;
import com.vsj.model.VsjMemberRelationSet;
import com.vsj.model.VsjRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname MemberServiceImpl
 * @Description
 * @Date 2019/8/27 9:06
 * @Created by wangzx
 */
@Service("memberService")
public class MemberServiceImpl implements MemberService {

    @Autowired
    private VsjMemberDAO vsjMemberDAO;
    @Autowired
    private MemberRelationDao memberRelationDao;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private VsjMemberProfitDAO vsjMemberProfitDAO;
    @Autowired
    private VsjOrderDAO vsjOrderDAO;


    @Override
    public BaseResponseParam disposeRelation(QueryStat queryStat) {
        // 1、判断后台设置关系开关是否开启
        VsjMemberRelationSet relation = memberRelationDao.getMemberRelation(queryStat.getPlatformCode());
        Byte relationSwitch = relation.getRelationSwitch();
        if(relationSwitch == 1){
            // 1.2、 如果设置开关开启 处理下面业务

            // 判断邀请人是否可以成为推广人
            boolean inviterBool = getInviterBool(queryStat,relation);

            // 判断被邀请人是否可以成为下线
            boolean inviteeBool = getInviteeBool(queryStat,relation);

            if(inviterBool && inviteeBool){
                // 被邀请人可以成为推广人并且邀请人可以成为下线,添加上下级关系
                setRelation(queryStat);
                return  BaseResponseParam.success();
            }
            return  BaseResponseParam.success();
        }else {
            // 1.1、 如果设置关闭，直接返回
            return BaseResponseParam.success();
        }
    }

    /**
     * @Description 添加邀请关系
     * @Author  wangzx
     * @Date   2019/8/1 13:40
     * @Param  [queryStat]
     * @Return  void
     * @Exception
     *
     */
    private void setRelation(QueryStat queryStat) {
        // 判断该会员是否有邀请人
        VsjMember vsjMember = vsjMemberDAO.selectByRegId(queryStat.getRegId());
        if(vsjMember != null && vsjMember.getParentId() == null){
            VsjMember parentMember = vsjMemberDAO.selectByRegId(vsjMember.getParentId());
            // 没有邀请人则添加邀请关系
            vsjMember.setParentId(queryStat.getReferrerId());
            vsjMember.setParentName(parentMember.getParentName());
            int count = vsjMemberDAO.updateVsjMember(vsjMember);
            // 查询自己及所有的上级 增加自己及所有上级的一级、一二级团队总数
            List<Integer> ids = vsjMemberDAO.getTotalSuperMemberIds(vsjMember.getId());
            ids.stream().forEach(memberId->{
                // 更新一级、二级团队数量
                VsjMemberProfit vsjMemberProfit = new VsjMemberProfit();
                vsjMemberProfit.setMemberId(memberId);
                vsjMemberProfit.setFirstLowerCount(1);
                vsjMemberProfit.setSecondLowerCount(1);
                vsjMemberProfit.setLowerLevelCount(1);
                vsjMemberProfitDAO.updateByMemberId(vsjMemberProfit);
            });
        }
    }

    /**
     * @Description 判断被邀请人是否可以成为下线
     * @Author  wangzx
     * @Date   2019/8/1 13:35
     * @Param  [queryStat]
     * @Return   boolean
     * @Exception
     *
     */
    private boolean getInviteeBool(QueryStat queryStat,VsjMemberRelationSet relation) {
        boolean flag = false;
        Integer con = relation.getOfflineConditions();
        if(queryStat.getType() == con){
            flag = true;
        }
        return flag;
    }

    /**
     * @Description 判断邀请人是否可以成为推广人
     * @Author  wangzx
     * @Date   2019/8/1 13:35
     * @Param  [queryStat]
     * @Return  boolean
     * @Exception
     *
     */
    private boolean getInviterBool(QueryStat queryStat,VsjMemberRelationSet relation) {
        boolean flag = false;
        if(relation.getNeedRequire() == 0){
            // 成为推广人没有条件
            flag = true;
        }else {
            // 成为推广人有条件
            if(relation.getBindPhone() == 1){
                // 绑定手机
                VsjRegister vsjRegister = userDAO.getUserById(queryStat.getRegId());
                if(vsjRegister != null && StringUtil.isNoEmptyStr(vsjRegister.getTelPhone())){
                    flag = true;
                }
            }else if(relation.getExpenseMoney() != null){
                // 总消费多少钱
                VsjMember vsjMember = vsjMemberDAO.selectByRegId(queryStat.getRegId());
                VsjMemberProfit vsjMemberProfit = vsjMemberProfitDAO.getMemberProfitByMemberId(vsjMember.getId(),queryStat.getPlatformCode());
                if(vsjMemberProfit.getCompletOrderResults().compareTo(relation.getExpenseMoney()) > -1){
                    flag = true;
                }
            }else if(relation.getExpenseNum() != null){
                // 总消费次数
                VsjMember vsjMember = vsjMemberDAO.selectByRegId(queryStat.getRegId());
                VsjMemberProfit vsjMemberProfit = vsjMemberProfitDAO.getMemberProfitByMemberId(vsjMember.getId(),queryStat.getPlatformCode());
                if(vsjMemberProfit.getCompletOrder() >= relation.getExpenseNum()){
                    flag = true;
                }
            }else if(StringUtil.isNoEmptyStr(relation.getProductIds())){
                // 查询系统配置的指定购买产品id
                String[] ids = relation.getProductIds().split(",");
                // 查询该会员购过的产品ids
                VsjMember vsjMember = vsjMemberDAO.selectByRegId(queryStat.getRegId());
                List<String> productIds = vsjOrderDAO.getProductIdsByMemberId(vsjMember.getId());
                if(!productIds.isEmpty()){
                    for (String id : ids) {
                        if(productIds.contains(id)){
                            // 如果该会员购买过的商品包含指定商品
                            flag = true;
                        }
                    }
                }
            }else {
                // 以上配置都没有
                flag = false;
            }
        }
        return flag;
    }
}
