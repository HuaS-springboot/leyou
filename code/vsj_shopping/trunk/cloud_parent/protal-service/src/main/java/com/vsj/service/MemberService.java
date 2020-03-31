package com.vsj.service;

import com.vsj.common.model.request.QueryStat;

public interface MemberService {
    Object getMemberList(QueryStat queryStat);

    Object  getBalanceDeTail(Integer userId);

    Object getTransactionDetail(Integer userId);

    Object getBonusDetails(Integer userId,Integer isSettle);

    Object getMaterialList(QueryStat queryStat);
}
