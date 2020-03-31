package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;

public interface MemberProfitService {
    BaseResponseParam getMemberDetail(Integer memberId, Integer platformCode);
}
