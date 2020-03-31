package com.vsj.service.impl;

import com.vsj.dao.VsjMemberProfitDAO;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.MemberTeamResponse;
import com.vsj.service.MemberProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname MemberProfitServiceImpl
 * @Description 会员分润实现
 * @Date 2019/8/26 10:40
 * @Created by wangzx
 */
@Service
public class MemberProfitServiceImpl implements MemberProfitService {

    @Autowired
    private VsjMemberProfitDAO vsjMemberProfitDAO;

    @Override
    public List<MemberTeamResponse> getMemberProfitList(BaseQueryStat baseQueryStat) {
        return vsjMemberProfitDAO.getMemberProfitList(baseQueryStat);
    }
}
