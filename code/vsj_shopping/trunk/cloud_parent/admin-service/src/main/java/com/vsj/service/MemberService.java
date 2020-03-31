package com.vsj.service;


import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.MemberEditDetail;
import com.vsj.common.model.request.QueryStat;
import com.vsj.model.response.MemberResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: HuaS
 * @Date :2019/8/2 17:30
 * @Describe:
 */

public interface MemberService {
    Object getMemberList(QueryStat queryStat);

    BaseResponseParam editMemberDetail(MemberEditDetail memberEditDetail);

    boolean exportMemberListExcel(QueryStat queryStat, HttpServletResponse response);

}
