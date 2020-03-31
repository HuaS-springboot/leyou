package com.vsj.service.impl;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.dao.VsjMemberProfitDAO;
import com.vsj.model.VsjMemberProfit;
import com.vsj.service.MemberProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberProfitServiceImpl implements MemberProfitService {

    @Autowired
    private VsjMemberProfitDAO vsjMemberProfitDAO;

    @Override
    public BaseResponseParam getMemberDetail(Integer memberId, Integer platformCode) {
        VsjMemberProfit memberProfit = vsjMemberProfitDAO.getMemberProfitByMemberId(memberId,platformCode);
        return BaseResponseParam.success(memberProfit);
    }
}
