package com.vsj.dao;

import com.vsj.mapper.MemberMapper;
import com.vsj.model.RegisterRecord;
import com.vsj.model.VsjMemberProfit;
import com.vsj.model.VsjOrderReceiptsRecord;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.MemberResponse;
import com.vsj.model.response.UserBalanceResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: HuaS
 * @Date :2019/8/2 17:27
 * @Describe:
 */
@Component
public class MemberDao {
    @Autowired
    private MemberMapper memberMapper;

    public List<MemberResponse> getMemberList(BaseQueryStat queryStat) {
        return memberMapper.getMemberList(queryStat);
    }

    public UserBalanceResponse getBalanceDeTail(Integer userId){
        return memberMapper.getBalanceDeTail(userId);
    }

    public RegisterRecord getTransactionDetail(Integer userId){
        return memberMapper.getTransactionDetail(userId);
    }

    public VsjMemberProfit memberProfit(Integer userId){
        return memberMapper.memberProfit(userId);
    }

    public List<VsjOrderReceiptsRecord> orderReceiptsRecord(Integer userId,Integer isSettle){
        return memberMapper.orderReceiptsRecord(userId,isSettle);
    }

}
