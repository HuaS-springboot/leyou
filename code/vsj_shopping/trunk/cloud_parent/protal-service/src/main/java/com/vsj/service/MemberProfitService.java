package com.vsj.service;

import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.MemberTeamResponse;

import java.util.List;

/**
 * @Classname MemberProfitService
 * @Description 会员分润接口相关
 * @Date 2019/8/26 10:40
 * @Created by wangzx
 */
public interface MemberProfitService {

    List<MemberTeamResponse> getMemberProfitList(BaseQueryStat baseQueryStat);
}
